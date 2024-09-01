package com.example.domain.usecase

import com.example.domain.repository.ContactRepository
import javax.inject.Inject

class ReadMostCallUsersUseCase @Inject constructor(private val contactsRepository: ContactRepository) {
    operator fun invoke() = contactsRepository.readMostCallUsers()
}