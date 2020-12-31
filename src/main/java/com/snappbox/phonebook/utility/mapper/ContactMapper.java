package com.snappbox.phonebook.utility.mapper;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.dto.ContactDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper extends BaseMapper<ContactEntity, ContactDto> {
}
