package com.snappbox.phonebook.service.impl;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.dto.ContactDto;
import com.snappbox.phonebook.repository.BaseRepository;
import com.snappbox.phonebook.repository.ContactRepository;
import com.snappbox.phonebook.repository.ContactSpecification;
import com.snappbox.phonebook.service.ContactService;
import com.snappbox.phonebook.service.GithubService;
import com.snappbox.phonebook.utility.ContactStatus;
import com.snappbox.phonebook.utility.mapper.BaseMapper;
import com.snappbox.phonebook.utility.mapper.ContactMapper;
import com.snappbox.phonebook.utility.search.SearchDto;
import com.snappbox.phonebook.utility.search.SpecificationsBuilder;
import io.github.resilience4j.retry.Retry;
import io.vavr.CheckedFunction0;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * This class consists of all services of {@link ContactEntity}
 *
 * @author Roghayeh Farhadi
 */
@Service
public class ContactServiceImpl extends BaseServiceImpl<ContactEntity, ContactDto> implements ContactService {

    private final SpecificationsBuilder<ContactEntity, ContactSpecification> contactSpecBuilder;
    private final ContactRepository contactRepository;
    private final GithubService githubService;
    private final ContactMapper contactMapper;
    private final Retry retry;

    public ContactServiceImpl(BaseRepository<ContactEntity> baseRepository
            , BaseMapper<ContactEntity, ContactDto> baseMapper
            , SpecificationsBuilder<ContactEntity, ContactSpecification> contactSpecBuilder
            , ContactRepository contactRepository, GithubService githubService
            , ContactMapper contactMapper, Retry retry) {

        super(baseRepository, baseMapper);
        this.contactSpecBuilder = contactSpecBuilder;
        this.contactRepository = contactRepository;
        this.githubService = githubService;
        this.contactMapper = contactMapper;
        this.retry = retry;
    }

    /**
     * A {@link ContactEntity} with PENDING {@link ContactStatus} persists in db
     * and then it performs async call to @see "https://api.github.com/users/{username}/repos"
     * and receive all repositories of contact and  changes  {@link ContactStatus} to SUCCESS
     * when poor connection it performs the retry several times
     *
     * @param dto is contact information
     * @return {@link ContactEntity} added in db
     */
    @Override
    public ContactEntity save(ContactDto dto) {
        ContactEntity contactEntity = super.save(dto);
        CompletableFuture.runAsync(() -> updateContactRepositories(contactEntity));
        return contactEntity;
    }

    /**
     * dynamic search across contacts
     *
     * @param contactDto consist of search key and values that it's optional
     * @return {@link ContactEntity}
     */
    @Override
    public Page<ContactEntity> search(ContactDto contactDto) {
        SearchDto searchDto = contactMapper.toSearchDto(contactDto);
        Specification<ContactEntity> specification = contactSpecBuilder.build(searchDto, ContactSpecification.class);
        return contactRepository.findAll(specification, searchDto.getPageable());
    }

    /**
     * Call @see "https://api.github.com/users/{username}/repos"
     * and receive all repositories of contact and  changes  {@link ContactStatus} to SUCCESS
     * when poor connection it performs the retry several times
     *
     * @param contactEntity {@link ContactEntity}
     */
    @Override
    public void updateContactRepositories(ContactEntity contactEntity) {
        CheckedFunction0<Set<String>> retryingFlightSearch =
                Retry.decorateCheckedSupplier(retry,
                        () -> githubService.getContactRepositoryNamesByGithub(contactEntity));
        try {
            Set<String> repoNames = retryingFlightSearch.apply();
            contactEntity.setRepositories(repoNames);
            contactEntity.setStatus(ContactStatus.SUCCESS);
            contactRepository.save(contactEntity);
        } catch (Throwable ignore) {
        }
    }
}
