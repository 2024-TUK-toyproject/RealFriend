package com.example.domain.entity.album

data class AlbumInfo(
    val albumName: String = "기본 앨범",
    val albumMemberCount: Int = 1,
    val albumPictureCount: Int = 0,
    val currentUsage: Double = 0.0,
    val totalUsage: Double = 0.0
)