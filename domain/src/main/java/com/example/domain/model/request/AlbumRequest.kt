package com.example.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class AlbumRequest(
    val albumId: String,
    val albumName: String,
    val albumThumbnail: String,
)
