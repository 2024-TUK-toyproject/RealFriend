package com.example.domain.repository

import com.example.domain.entity.contact.ContactInfo
import com.example.domain.entity.notification.FriendRequestInfo
import com.example.domain.model.ApiState
import com.example.domain.model.request.ContactRequest
import kotlinx.coroutines.flow.Flow

interface FriendRepository {

    fun acceptFriendRequest(friendId: String): Flow<ApiState<Unit>>

    fun readAllFriendRequest(): Flow<ApiState<Map<String, List<FriendRequestInfo>>>>

    fun readAllContact(contacts: List<ContactRequest>): Flow<ApiState<List<ContactInfo>>>


    fun addFriend(friendId: String): Flow<ApiState<Unit>>

    fun deleteFriend(friendIds: List<String>): Flow<ApiState<Unit>>

}