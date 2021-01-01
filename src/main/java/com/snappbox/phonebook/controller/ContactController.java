package com.snappbox.phonebook.controller;

import com.snappbox.phonebook.dto.ContactDto;
import com.snappbox.phonebook.dto.operationType.PersistOperation;
import com.snappbox.phonebook.utility.constant.UrlMappings;
import com.snappbox.phonebook.utility.mapper.ContactMapper;
import com.snappbox.phonebook.service.ContactService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roghayeh Farhadi
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(UrlMappings.CONTACTS)
public class ContactController {

    private final ContactService contactService;
    private final ContactMapper contactMapper;

    @ApiOperation(value = "create new contact using:name, phoneNumber, email, organization, github"
    ,notes =  "All values are required")
    @PostMapping
    public ResponseEntity<Object> addContact(@RequestBody @Validated(PersistOperation.class) ContactDto contactDto) {
        contactService.save(contactDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Search across contacts ",notes = "All values are optional")
    @PostMapping("/search")
    public ResponseEntity<Page<ContactDto>> searchContact(@RequestBody(required = false) ContactDto contactDto) {
        return ResponseEntity.ok(contactService.search(contactDto).map(contactMapper::toDto));
    }

}
