package com.snappbox.phonebook.utility;

/**
 * This enum specify contact  persist status
 *
 * @author Roghayeh Farhadi
 */
public enum ContactStatus {
    /**
     * {@link com.snappbox.phonebook.domain.ContactEntity} with this status waits for
     * call @see "https://api.github.com/users/{username}/repos" for receive own repositories
     */
    PENDING,
    /**
     * {@link com.snappbox.phonebook.domain.ContactEntity} with this status have received
     *   own repositories
     */
    SUCCESS
}
