package com.snappbox.phonebook.service.impl;

import com.snappbox.phonebook.domain.BaseEntity;
import com.snappbox.phonebook.dto.BaseDto;
import com.snappbox.phonebook.utility.mapper.BaseMapper;
import com.snappbox.phonebook.repository.BaseRepository;
import com.snappbox.phonebook.service.BaseSerVice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BaseServiceImpl<E extends BaseEntity, D extends BaseDto> implements BaseSerVice<E, D> {

    private final BaseRepository<E> baseRepository;
    private final BaseMapper<E, D> baseMapper;

    @Override
    public E save(D dto) {
        E entity = baseMapper.toEntity(dto);
        return baseRepository.save(entity);
    }
}
