package com.snappbox.phonebook.utility.mapper;

import com.snappbox.phonebook.domain.BaseEntity;
import com.snappbox.phonebook.dto.BaseDto;

/**
 * This interface is parent for all mappers
 *
 * @author Roghayeh Farhadi
 */
public interface BaseMapper<E extends BaseEntity, D extends BaseDto> {
    E toEntity(D d);

    D toDto(E entity);
}
