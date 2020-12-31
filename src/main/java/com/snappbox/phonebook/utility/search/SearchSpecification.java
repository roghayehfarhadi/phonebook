package com.snappbox.phonebook.utility.search;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
public abstract class SearchSpecification<E> implements Specification<E> {
    private SearchCriteria searchCriteria;

}
