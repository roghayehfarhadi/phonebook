package com.snappbox.phonebook.utility;


import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.dto.ContactDto;
import com.snappbox.phonebook.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestDataUtil {

    private final ContactService contactService;

    public ContactEntity createContact(ContactDto contactDto) {
        return contactService.save(contactDto);
    }
}
