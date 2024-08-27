package com.example.domain.usecase.friend

import com.example.domain.model.ApiState
import com.example.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFriendUseCase @Inject constructor(private val friendRepository: FriendRepository) {
    operator fun invoke(friendIds: List<String>): Flow<ApiState<Unit>> = friendRepository.deleteFriend(friendIds)
}