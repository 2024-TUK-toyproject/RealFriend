package com.example.domain.usecase.friend

import com.example.domain.model.ApiState
import com.example.domain.model.response.Friend
import com.example.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAllFriendsUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    operator fun invoke(): Flow<ApiState<List<Friend>>> = contactRepository.readAllFriends()
}