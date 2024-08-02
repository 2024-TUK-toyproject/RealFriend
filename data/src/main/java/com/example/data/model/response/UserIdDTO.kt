package com.example.data.model.response

import com.example.domain.model.UserId
import kotlinx.serialization.Serializable

@Serializable
data class UserIdDTO(
    val userId: Long?
)


fun UserIdDTO.asDomain() = UserId(
    userId = this.userId ?: 0
)