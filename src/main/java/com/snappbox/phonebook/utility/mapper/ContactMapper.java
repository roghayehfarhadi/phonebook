package com.snappbox.phonebook.utility.mapper;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.dto.ContactDto;
import com.snappbox.phonebook.utility.constant.ContactConst;
import com.snappbox.phonebook.utility.search.SearchCriteria;
import com.snappbox.phonebook.utility.search.SearchDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.snappbox.phonebook.utility.constant.ContactConst.GITHUB_KEY;

/**
 * This interface maps {@link ContactEntity } to {@link ContactDto} and vice versa
 *
 * @author Roghayeh Farhadi
 */
@Mapper(componentModel = "spring")
public interface ContactMapper extends BaseMapper<ContactEntity, ContactDto> {

    default SearchDto toSearchDto(ContactDto contactDto) {
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
