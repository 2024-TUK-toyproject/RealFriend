package com.example.domain.model.response.album

import kotlinx.serialization.Serializable

@Serializable
data class PictureIdResponse(
    val photoId: String,
    val photoUrl: String
)
