package com.example.domain.model.response

import com.example.domain.model.notification.FriendRequest
import kotlinx.serialization.Serializable

@Serializable
data class FriendRequestResponse(
    val friendRequestId: Long?,
    val name: String?,
    val phone: String?,
    val profileImage: String?,
    val time: String?,
    val userId: String?,
)


fun FriendRequestResponse.asDomain() = FriendRequest(
    friendRequestId = friendRequestId?.toLong() ?: 0L,
    name = name ?: "",
    phone = phone ?: "",
    profileImage = profileImage ?: "",
    time = time ?: "",
    userId = userId?.toLong() ?: 0L
)