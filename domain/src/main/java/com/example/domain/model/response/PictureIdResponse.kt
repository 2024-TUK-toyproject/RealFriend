package com.example.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PictureIdResponse(
    val photoId: String,
    val photoUrl: String
)
