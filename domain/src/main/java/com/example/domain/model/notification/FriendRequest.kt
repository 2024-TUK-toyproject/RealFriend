package com.example.domain.model.notification

data class FriendRequest(
    val friendRequestId: Long,
    val name: String,
    val phone: String,
    val profileImage: String,
    val time: String,
    val userId: Long,
)
