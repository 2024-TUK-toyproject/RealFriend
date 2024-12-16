package com.example.domain.model.response.album

import com.example.domain.entity.album.AlbumId
import kotlinx.serialization.Serializable

@Serializable
data class AlbumIdResponse(
    val albumId: Long,
)


fun AlbumIdResponse.toEntity() =
    AlbumId(albumId = this.albumId)





