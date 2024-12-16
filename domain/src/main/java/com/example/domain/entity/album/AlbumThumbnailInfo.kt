package com.example.domain.entity.album

data class AlbumThumbnailInfo(
    val albumId: AlbumId,
    val albumName: String,
    val albumThumbnail: String,
    val albumMemberCount: Int,
    val albumPictureCount: Int,
    val isStared: Boolean,
)
