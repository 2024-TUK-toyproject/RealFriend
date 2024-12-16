package com.example.domain.usecase

import com.example.domain.entity.contact.Contact
import com.example.domain.repository.ContactRepository
import javax.inject.Inject

class SyncContactsUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    operator fun invoke(userId: Long, contacts: List<Contact>) =
        contactRepository.syncContacts(userId, contacts)
}