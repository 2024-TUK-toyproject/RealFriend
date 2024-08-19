package com.example.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val accessToken: String?,
    val refreshToken: String?
)
