package com.example.domain.usecase.friend

import com.example.domain.model.ApiState
import com.example.domain.entity.notification.FriendRequestInfo
import com.example.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAllFriendRequestUseCase @Inject constructor(private val friendRepository: FriendRepository) {
    operator fun invoke(): Flow<ApiState<Map<String, List<FriendRequestInfo>>>> =
        friendRepository.readAllFriendRequest()
}