package com.snappbox.phonebook.utility.search;

import com.snappbox.phonebook.utility.Pagination;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This dto class present criteria list defined by end user for dynamic query
 *
 * @author Roghayeh Farhadi
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto  extends Pagination {
    private List<SearchCriteria> criteriaList = new ArrayList<>();
}
