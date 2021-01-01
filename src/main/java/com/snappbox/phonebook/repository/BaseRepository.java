package com.snappbox.phonebook.repository;

import com.snappbox.phonebook.domain.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is parent for all repositories
 *
 * @author Roghayeh Farhadi
 */
@Repository
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
}
