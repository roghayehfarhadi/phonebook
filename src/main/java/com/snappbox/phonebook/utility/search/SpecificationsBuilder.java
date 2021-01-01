package com.snappbox.phonebook.utility.search;

import com.snappbox.phonebook.domain.BaseEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class create {@link Specification} from user criteria
 *
 * @author Roghayeh Farhadi
 */
@Component
public class SpecificationsBuilder<E extends BaseEntity, S extends Specification<E>> {

    public Specification<E> build(SearchDto searchDto, Class<S> specClass) {
        List<SearchCriteria> criteriaList = searchDto.getCriteriaList();
        if (criteriaList == null || criteriaList.size() == 0) {
            return null;
        }
        List<S> specificationList =
                criteriaList
                        .stream()
                        .map(criteria -> {
                            S spec = null;
                            try {
                                spec = specClass.newInstance();
                                ((SearchSpecification) spec).setSearchCriteria(criteria);
                            } catch (InstantiationException | IllegalAccessException e) {
                                System.out.println(e);
                            }
                            return spec;
                        }).collect(Collectors.toList());

        Specification<E> specification = specificationList.get(0);
        for (int i = 1; i < specificationList.size(); i++) {
            specification = Specification.where(specification).and(specificationList.get(i));
        }
        return specification;
    }
}
