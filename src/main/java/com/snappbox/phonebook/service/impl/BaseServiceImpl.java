package com.snappbox.phonebook.service.impl;

import com.snappbox.phonebook.domain.BaseEntity;
import com.snappbox.phonebook.dto.BaseDto;
import com.snappbox.phonebook.utility.mapper.BaseMapper;
import com.snappbox.phonebook.repository.BaseRepository;
import com.snappbox.phonebook.service.BaseSerVice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class is parent for all services
 *
 * @author Roghayeh Farhadi
 */
@RequiredArgsConstructor
@Service
public class BaseServiceImpl<E extends BaseEntity, D extends BaseDto> implements BaseSerVice<E, D> {

    private final BaseRepository<E> baseRepository;
    private final BaseMapper<E, D> baseMapper;

    /**
     * convert dto to entity and persist it on db
     * @param dto any dto that extends {@link BaseDto}
     * @return any entity that extends {@link com.snappbox.phonebook.domain.ContactEntity}
     */
    @Override
    public E save(D dto) {
        E entity = baseMapper.toEntity(dto);
        return baseRepository.save(entity);
    }
}
