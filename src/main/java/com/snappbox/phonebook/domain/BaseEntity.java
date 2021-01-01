package com.snappbox.phonebook.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class is parent for all entity
 *
 * @author Roghayeh Farhadi
 */
@Setter
@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    LocalDateTime localDateTime;

    @UpdateTimestamp
    LocalDateTime updateDate;
}
