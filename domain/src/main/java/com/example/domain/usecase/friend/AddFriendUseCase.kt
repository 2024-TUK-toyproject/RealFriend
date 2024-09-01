package com.example.domain.usecase.friend

import com.example.domain.model.ApiState
import com.example.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFriendUseCase @Inject constructor(private val friendRepository: FriendRepository) {
    operator fun invoke(friendRequest: Long): Flow<ApiState<Unit>> =
        friendRepository.addFriend(friendRequest.toString())
}