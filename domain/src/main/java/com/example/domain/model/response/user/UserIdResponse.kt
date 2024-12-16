package com.example.domain.model.response.user

import com.example.domain.entity.user.UserId
import kotlinx.serialization.Serializable

@Serializable
data class UserIdResponse(
    val userId: String?
)


fun UserIdResponse.toEntity() = UserId(
    userId = this.userId?.toLong() ?: 0
)