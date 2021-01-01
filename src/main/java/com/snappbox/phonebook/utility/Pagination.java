package com.snappbox.phonebook.utility;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * This class creates a {@link Pageable}
 *
 * @author Roghayeh Farhadi
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    private int page = 0;
    private int size = 1000;

    @JsonIgnore
    public Pageable getPageable() {
        return PageRequest.of(getPage(), getSize());
    }

}
