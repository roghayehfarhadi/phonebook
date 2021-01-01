package com.snappbox.phonebook.repository;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.utility.ContactStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface consist of all query methods of {@link ContactEntity}
 *
 * @author Roghayeh Farhadi
 */
@Repository
public interface ContactRepository extends BaseRepository<ContactEntity> {

    Page<ContactEntity> findAll(Specification<ContactEntity> specification, Pageable pageable);

    List<ContactEntity> findByStatus(ContactStatus contactStatus);
}

