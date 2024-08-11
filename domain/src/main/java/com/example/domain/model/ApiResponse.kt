package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<D>(
    val content : D? = null,
    val message: String = "",
    val status: String = "",
)

