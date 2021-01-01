package com.snappbox.phonebook.service.impl;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.dto.RepositoryResponseDto;
import com.snappbox.phonebook.service.GithubService;
import com.snappbox.phonebook.utility.ContactStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class related to github webservice call
 *
 * @author Roghayeh Farhadi
 */
@RequiredArgsConstructor
@Service
public class GithubServiceImpl implements GithubService {

    @Value("${github.baseUrl}")
    private String baseUrl;

    @Value("${github.path}")
    private String path;

    private final RestTemplate restTemplate;

    /**
     * Call @see "https://api.github.com/users/{username}/repos"
     * and receive all repositories of contact
     *
     * @param contactEntity {@link ContactEntity}
     * @return Set<String> all repositories of contact
     */

    @Override
    public Set<String> getContactRepositoryNamesByGithub(ContactEntity contactEntity) {
        ResponseEntity<RepositoryResponseDto[]> response = restTemplate.getForEntity(
                baseUrl + contactEntity.getGithub() + path, RepositoryResponseDto[].class);

        return Stream.of(Objects.requireNonNull(response.getBody()))
                .filter(Objects::nonNull)
                .map(RepositoryResponseDto::getName)
                .collect(Collectors.toSet());
    }

}
