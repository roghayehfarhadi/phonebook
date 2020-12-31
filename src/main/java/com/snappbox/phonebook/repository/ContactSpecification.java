package com.snappbox.phonebook.repository;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.utility.search.SearchCriteria;
import com.snappbox.phonebook.utility.search.SearchOperation;
import com.snappbox.phonebook.utility.search.SearchSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static com.snappbox.phonebook.utility.constant.ContactConst.*;

public class ContactSpecification extends SearchSpecification<ContactEntity> {

    private Predicate predicate = null;

    @Override
    public Predicate toPredicate(Root<ContactEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        SearchCriteria searchCriteria = super.getSearchCriteria();
        String key = searchCriteria.getKey();
        Object value = searchCriteria.getValue();
        SearchOperation operation = searchCriteria.getOperation();

        if (operation == SearchOperation.EQUALITY) {
            switch (key) {
                case NAME_KEY:
                    predicate = criteriaBuilder.equal(root.get(NAME_KEY), value);
                    break;
                case PHONE_NUMBER_KEY:
                    predicate = criteriaBuilder.equal(root.get(PHONE_NUMBER_KEY), value);
                    break;
                case EMAIL_KEY:
                    predicate = criteriaBuilder.equal(root.get(EMAIL_KEY), value);
                    break;
                case ORGANIZATION_KEY:
                    predicate = criteriaBuilder.equal(root.get(ORGANIZATION_KEY), value);
                    break;
                case GITHUB_KEY:
                    predicate = criteriaBuilder.equal(root.get(GITHUB_KEY), value);
            }
        }

        return predicate;
    }
}
