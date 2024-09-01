package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.login.Contact
import com.example.domain.model.notification.FriendRequest
import com.example.domain.model.request.ContactRequest
import com.example.domain.model.response.ContactInfo
import kotlinx.coroutines.flow.Flow

interface FriendRepository {

    fun acceptFriendRequest(friendId: String): Flow<ApiState<Unit>>

    fun readAllFriendRequest(): Flow<ApiState<Map<String, List<FriendRequest>>>>

    fun readAllContact(contacts: List<ContactRequest>): Flow<ApiState<List<ContactInfo>>>


    fun addFriend(friendId: String): Flow<ApiState<Unit>>

    fun deleteFriend(friendIds: List<String>): Flow<ApiState<Unit>>

}