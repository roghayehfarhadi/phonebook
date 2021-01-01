package com.snappbox.phonebook.domain;

import com.snappbox.phonebook.utility.ContactStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
/**
 * this class consist of all information of a contact
 *
 * @author Roghayeh Farhadi
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contact")
public class ContactEntity extends BaseEntity {

    private String name;
    private String phoneNumber;
    private String email;
    private String organization;
    private String github;
    @Enumerated(value = EnumType.STRING)
    private ContactStatus status = ContactStatus.PENDING;
    @ElementCollection
    @Column(name = "repository_name")
    private Set<String> repositories = new HashSet<>();


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

    public ContactStatus getStatus() {
        return status;
    }

    public void setStatus(ContactStatus status) {
        this.status = status;
    }

    public Set<String> getRepositories() {
        return repositories;
    }

    public void setRepositories(Set<String> repositories) {
        this.repositories = repositories;
    }
}



