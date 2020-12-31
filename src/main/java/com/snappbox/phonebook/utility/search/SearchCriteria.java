package com.snappbox.phonebook.utility.search;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation = SearchOperation.EQUALITY;

    public SearchCriteria(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}