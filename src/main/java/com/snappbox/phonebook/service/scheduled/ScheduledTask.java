package com.snappbox.phonebook.service.scheduled;

import com.snappbox.phonebook.repository.ContactRepository;
import com.snappbox.phonebook.service.ContactService;
import com.snappbox.phonebook.utility.ContactStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class performs scheduled tasks with a fix delay
 *
 * @author Roghayeh Farhadi
 */
@RequiredArgsConstructor
@Component
public class ScheduledTask {

    private final ContactRepository contactRepository;
    private final ContactService contactService;

    /**
     * Find all {@link com.snappbox.phonebook.domain.ContactEntity} with PENDING {@link ContactStatus}
     * Call @see "https://api.github.com/users/{username}/repos"
     * and receive all repositories of contact and  changes  {@link ContactStatus} to SUCCESS
     */
    @Transactional
    @Scheduled(fixedDelayString = "${scheduled.contactRepository}")
    public void updateContactRepositories() {
        contactRepository.findByStatus(ContactStatus.PENDING)
                .forEach(contactService::updateContactRepositories);
    }
}
