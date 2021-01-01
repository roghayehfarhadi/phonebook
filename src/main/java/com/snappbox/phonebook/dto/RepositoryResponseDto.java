package com.snappbox.phonebook.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * this class is response of call github webservice
 *
 * @author Roghayeh Farhadi
 */
@Getter
@Setter
public class RepositoryResponseDto {
    /**
     * repository name of contact
     */
    private String name;
}
