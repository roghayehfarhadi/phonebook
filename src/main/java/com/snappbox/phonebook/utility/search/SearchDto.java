package com.snappbox.phonebook.utility.search;

import com.snappbox.phonebook.utility.Pagination;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto  extends Pagination {
    private List<SearchCriteria> criteriaList = new ArrayList<>();
}
