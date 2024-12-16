package com.example.domain.entity.notification

data class FriendRequestInfo(
    val friendRequestId: Long,
    val name: String,
    val phone: String,
    val profileImage: String,
    val time: String,
    val userId: Long,
)
