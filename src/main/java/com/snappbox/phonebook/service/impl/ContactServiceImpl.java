package com.snappbox.phonebook.service.impl;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.dto.ContactDto;
import com.snappbox.phonebook.repository.BaseRepository;
import com.snappbox.phonebook.repository.ContactRepository;
import com.snappbox.phonebook.repository.ContactSpecification;
import com.snappbox.phonebook.service.ContactService;
import com.snappbox.phonebook.service.GithubService;
import com.snappbox.phonebook.utility.ContactStatus;
import com.snappbox.phonebook.utility.constant.ContactConst;
import com.snappbox.phonebook.utility.mapper.BaseMapper;
import com.snappbox.phonebook.utility.search.SearchCriteria;
import com.snappbox.phonebook.utility.search.SearchDto;
import com.snappbox.phonebook.utility.search.SpecificationsBuilder;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static com.snappbox.phonebook.config.AsyncConfig.THREAD_POOL_GITHUB;
import static com.snappbox.phonebook.utility.constant.ContactConst.GITHUB_KEY;


@Service
public class ContactServiceImpl extends BaseServiceImpl<ContactEntity, ContactDto> implements ContactService {

    private final SpecificationsBuilder<ContactEntity, ContactSpecification> contactSpecBuilder;
    private final ContactRepository contactRepository;
    private final GithubService githubService;

    public ContactServiceImpl(BaseRepository<ContactEntity> baseRepository
            , BaseMapper<ContactEntity, ContactDto> baseMapper
            , SpecificationsBuilder<ContactEntity, ContactSpecification> contactSpecBuilder
            , ContactRepository contactRepository, GithubService githubService) {

        super(baseRepository, baseMapper);
        this.contactSpecBuilder = contactSpecBuilder;
        this.contactRepository = contactRepository;
        this.githubService = githubService;
    }

    @Override
    public ContactEntity save(ContactDto dto) {
        System.out.println("first thread:" +Thread.currentThread());
        ContactEntity contactEntity = super.save(dto);
        updateContactRepositories(contactEntity);
        return contactEntity;
    }

    @Override
    public Page<ContactEntity> search(ContactDto contactDto) {
        SearchDto searchDto = toSearchDto(contactDto);
        Specification<ContactEntity> specification = contactSpecBuilder.build(searchDto, ContactSpecification.class);
        return contactRepository.findAll(specification, searchDto.getPageable());
    }

    @Async(THREAD_POOL_GITHUB)
    @Retry(name = GITHUB_KEY)
    @Override
    public void updateContactRepositories(ContactEntity contactEntity) {
        System.out.println("second thread:" +Thread.currentThread());
        Set<String> repoNames = githubService.getContactRepositoryNamesByGithub(contactEntity);
        contactEntity.setRepositories(repoNames);
        contactEntity.setStatus(ContactStatus.SUCCESS);
        contactRepository.save(contactEntity);
    }


    private SearchDto toSearchDto(ContactDto contactDto) {
        return Optional.ofNullable(contactDto)
                .map(contDto -> {
                    SearchDto searchDto = new SearchDto();
                    List<SearchCriteria> criteria = new ArrayList<>();

                    Optional.ofNullable(contactDto.getName())
                            .ifPresent(name -> criteria.add(new SearchCriteria(ContactConst.NAME_KEY, name)));
                    Optional.ofNullable(contactDto.getEmail())
                            .ifPresent(email -> criteria.add(new SearchCriteria(ContactConst.EMAIL_KEY, email)));
                    Optional.ofNullable(contactDto.getGithub())
                            .ifPresent(github -> criteria.add(new SearchCriteria(GITHUB_KEY, github)));
                    Optional.ofNullable(contactDto.getOrganization())
                            .ifPresent(organization -> criteria.add(new SearchCriteria(ContactConst.ORGANIZATION_KEY, organization)));
                    Optional.ofNullable(contactDto.getPhoneNumber())
                            .ifPresent(phoneNumber -> criteria.add(new SearchCriteria(ContactConst.PHONE_NUMBER_KEY, phoneNumber)));

                    searchDto.setCriteriaList(criteria);
                    return searchDto;
                }).orElseGet(SearchDto::new);
    }
}
