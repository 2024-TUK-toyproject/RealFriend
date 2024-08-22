package com.example.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ContentRequest<T>(
    val content: T,
)
