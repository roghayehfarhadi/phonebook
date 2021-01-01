package com.snappbox.phonebook.service;

import com.snappbox.phonebook.domain.ContactEntity;

import java.util.Set;

/**
 * This interface is definition of all github services
 *
 * @author Roghayeh Farhadi
 */
public interface GithubService {
    Set<String> getContactRepositoryNamesByGithub(ContactEntity contactEntity);
}
