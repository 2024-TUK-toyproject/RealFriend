package com.example.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class FriendIdRequest(
    val friendId: String,
)
