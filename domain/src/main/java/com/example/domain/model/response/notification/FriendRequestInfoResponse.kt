package com.example.domain.model.response.notification

import com.example.domain.entity.notification.FriendRequestInfo
import kotlinx.serialization.Serializable

@Serializable
data class FriendRequestInfoResponse(
    val friendRequestId: Long?,
    val name: String?,
    val phone: String?,
    val profileImage: String?,
    val time: String?,
    val userId: String?,
)


fun FriendRequestInfoResponse.asDomain() = FriendRequestInfo(
    friendRequestId = friendRequestId?.toLong() ?: 0L,
    name = name ?: "",
    phone = phone ?: "",
    profileImage = profileImage ?: "",
    time = time ?: "",
    userId = userId?.toLong() ?: 0L
)