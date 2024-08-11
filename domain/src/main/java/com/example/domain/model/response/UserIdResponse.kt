package com.example.domain.model.response

import com.example.domain.model.UserId
import kotlinx.serialization.Serializable

@Serializable
data class UserIdResponse(
    val userId: String?
)


fun UserIdResponse.asDomain() = UserId(
    userId = this.userId?.toLong() ?: 0
)