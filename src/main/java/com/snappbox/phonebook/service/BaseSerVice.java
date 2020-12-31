package com.snappbox.phonebook.service;

import com.snappbox.phonebook.domain.BaseEntity;
import com.snappbox.phonebook.dto.BaseDto;

public interface BaseSerVice<E extends BaseEntity, D extends BaseDto> {
    E save(D dto);
}
