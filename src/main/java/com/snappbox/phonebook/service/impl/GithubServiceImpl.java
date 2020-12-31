package com.snappbox.phonebook.service.impl;

import com.snappbox.phonebook.domain.ContactEntity;
import com.snappbox.phonebook.dto.RepositoryResponseDto;
import com.snappbox.phonebook.service.ContactService;
import com.snappbox.phonebook.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.snappbox.phonebook.utility.constant.ContactConst.GITHUB_BASE_URL;
import static com.snappbox.phonebook.utility.constant.ContactConst.GITHUB_PTH;

@Service
public class GithubServiceImpl implements GithubService {

    @Override
    public Set<String> getContactRepositoryNamesByGithub(ContactEntity contactEntity) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RepositoryResponseDto[]> response =
                restTemplate.getForEntity(GITHUB_BASE_URL + contactEntity.getGithub() + GITHUB_PTH
                        , RepositoryResponseDto[].class);

        return Stream.of(Objects.requireNonNull(response.getBody()))
                .filter(Objects::nonNull)
                .map(RepositoryResponseDto::getName)
                .collect(Collectors.toSet());
    }

}
