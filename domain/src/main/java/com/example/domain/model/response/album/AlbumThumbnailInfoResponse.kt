package com.example.domain.model.response.album

import com.example.domain.entity.album.AlbumId
import com.example.domain.entity.album.AlbumThumbnailInfo
import kotlinx.serialization.Serializable

@Serializable
data class AlbumThumbnailInfoResponse(
    val albumId: String?,
    val albumName: String?,
    val albumThumbnail: String?,
    val albumMemberCount: Int?,
    val albumPictureCount: Int?,
    val isStared: Boolean?,
)


fun AlbumThumbnailInfoResponse.toEntity() = AlbumThumbnailInfo(
    albumId = albumId?.let { AlbumId(it.toLong()) } ?: AlbumId(0),
    albumName = albumName ?: "기본 앨범",
    albumThumbnail = albumThumbnail ?: "",
    albumMemberCount = albumMemberCount ?: 0,
    albumPictureCount = albumPictureCount ?: 0,
    isStared = isStared ?: false
)