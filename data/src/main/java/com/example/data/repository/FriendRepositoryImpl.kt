package com.example.data.repository

import com.example.data.network.FriendApi
import com.example.domain.model.ApiState
import com.example.domain.model.login.Contact
import com.example.domain.model.notification.FriendRequest
import com.example.domain.model.request.ContactRequest
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.request.FriendRequestIdRequest
import com.example.domain.model.request.toDTO
import com.example.domain.model.response.ContactInfo
import com.example.domain.model.response.asDomain
import com.example.domain.model.safeFlow2
import com.example.domain.model.safeFlowUnit
import com.example.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(val friendApi: FriendApi) : FriendRepository {
    override fun acceptFriendRequest(friendId: String): Flow<ApiState<Unit>> = safeFlowUnit {
        friendApi.acceptFriendRequest(FriendRequestIdRequest(friendId))
    }


    override fun readAllFriendRequest(): Flow<ApiState<Map<String, List<FriendRequest>>>> = safeFlow2 (
        { friendApi.readAllFriendRequest() }) { map ->
        map.mapValues { list -> list.value.map { it.asDomain() } }
    }

    override fun readAllContact(contacts: List<ContactRequest>): Flow<ApiState<List<ContactInfo>>> = safeFlow2 (
        { friendApi.readAllContact(ContentRequest(contacts)) }) { list ->
        list.map { it.asDomain() }
    }

    override fun addFriend(friendId: String): Flow<ApiState<Unit>> = safeFlowUnit {
        friendApi.addFriend(FriendIdRequest(friendId))
    }


    override fun deleteFriend(friendIds: List<String>): Flow<ApiState<Unit>> = safeFlowUnit {
        friendApi.deleteFriend(ContentRequest(friendIds.map { FriendIdRequest(it) }))
    }
}