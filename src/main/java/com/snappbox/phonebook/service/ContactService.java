package com.snappbox.phonebook.service;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.dto.ContactDto;
import org.springframework.data.domain.Page;


public interface ContactService extends BaseSerVice<ContactEntity, ContactDto> {

    Page<ContactEntity> search(ContactDto contactDto);

    void updateContactRepositories(ContactEntity contactEntity);

}
