package com.snappbox.phonebook.service;

import com.snappbox.phonebook.domain.ContactEntity;

import java.util.Set;

public interface GithubService {
    Set<String> getContactRepositoryNamesByGithub(ContactEntity contactEntity);
}
