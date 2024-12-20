package com.example.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CommentRequest(
    val photoId: String,
    val content: String
)
