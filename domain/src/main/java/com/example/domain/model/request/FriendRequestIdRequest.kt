package com.example.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class FriendRequestIdRequest(
    val friendRequestId: String
)
