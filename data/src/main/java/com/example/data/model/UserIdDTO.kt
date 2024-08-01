package com.example.data.model

import com.example.domain.model.response.UserId
import kotlinx.serialization.Serializable

@Serializable
data class UserIdDTO(
    val userId: Long?
)


fun UserIdDTO.asDomain() = UserId(
    userId = this.userId ?: 0
)