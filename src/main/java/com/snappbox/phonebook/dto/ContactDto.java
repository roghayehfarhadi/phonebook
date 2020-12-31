package com.snappbox.phonebook.dto;

import com.snappbox.phonebook.dto.operationType.PersistOperation;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Getter
//@Setter
public class ContactDto extends BaseDto {
    @NotBlank( groups = {PersistOperation.class})
    private String name;
    @NotBlank(groups = {PersistOperation.class})
    private String phoneNumber;
    @NotBlank(groups = {PersistOperation.class})
    private String email;
    @NotBlank(groups = {PersistOperation.class})
    private String organization;
    @NotBlank(groups = {PersistOperation.class})
    private String github;
    private Set<String> repositories;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public Set<String> getRepositories() {
        return repositories;
    }

    public void setRepositories(Set<String> repositories) {
        this.repositories = repositories;
    }
}
