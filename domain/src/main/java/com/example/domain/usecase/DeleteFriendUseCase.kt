package com.example.domain.usecase

import com.example.domain.model.ApiState
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFriendUseCase @Inject constructor(private val contactRepository: ContactRepository) {
    operator fun invoke(friendIds: List<String>): Flow<ApiState<Unit>> = contactRepository.deleteFriend(friendIds)
}