package com.example.domain.usecase.friend

import com.example.domain.model.login.Contact
import com.example.domain.model.request.toDTO
import com.example.domain.repository.FriendRepository
import javax.inject.Inject

class ReadAllContactsUseCase @Inject constructor(private val friendRepository: FriendRepository) {
    operator fun invoke(contacts: List<Contact>) = friendRepository.readAllContact(contacts.map {it.toDTO()})
}