package com.example.domain.entity.user

data class Friend(
    val userId: Long,
    val name: String,
    val phone: String,
    val profileImage: String,
    val isFriend: Boolean,
)
