package com.snappbox.phonebook.service.Scheduled;

import com.snappbox.phonebook.repository.ContactRepository;
import com.snappbox.phonebook.service.ContactService;
import com.snappbox.phonebook.utility.ContactStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ScheduledTask {

    private final ContactRepository contactRepository;
    private final ContactService contactService;

    @Transactional
    @Scheduled(fixedDelayString = "${scheduled.contactRepository}")
    public void updateContactRepositories() {
        contactRepository.findByStatus(ContactStatus.PENDING)
                .forEach(contactService::updateContactRepositories);
    }
}
