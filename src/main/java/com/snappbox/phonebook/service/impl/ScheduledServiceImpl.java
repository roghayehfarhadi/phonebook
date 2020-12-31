package com.snappbox.phonebook.service.impl;

import com.snappbox.phonebook.repository.ContactRepository;
import com.snappbox.phonebook.service.ContactService;
import com.snappbox.phonebook.service.ScheduledService;
import com.snappbox.phonebook.utility.ContactStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.snappbox.phonebook.utility.constant.ContactConst.SCHEDULED_VALUE;

@RequiredArgsConstructor
@Service
public class ScheduledServiceImpl implements ScheduledService {

    private final ContactRepository contactRepository;
    private final ContactService contactService;

    @Transactional
    @Scheduled(fixedDelay = 1800000)
    @Override
    public void updateContactRepositories() {
        contactRepository.findByStatus(ContactStatus.PENDING)
                .forEach(contactService::updateContactRepositories);
    }
}
