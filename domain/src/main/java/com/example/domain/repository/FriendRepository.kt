package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.notification.FriendRequest
import kotlinx.coroutines.flow.Flow

interface FriendRepository {

    fun acceptFriendRequest(friendId: String): Flow<ApiState<Unit>>

    fun readAllFriendRequest(): Flow<ApiState<Map<String, List<FriendRequest>>>>

    fun deleteFriend(friendIds: List<String>): Flow<ApiState<Unit>>

}