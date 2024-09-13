package com.example.domain.model.response

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

@Serializable
data class AlbumResponse(
    val albumId: String?,
    val albumName: String?,
    val albumThumbnail: String?,
    val albumMemberCount: Int?,
    val albumPictureCount: Int?,
    val isStared: Boolean?,
)

