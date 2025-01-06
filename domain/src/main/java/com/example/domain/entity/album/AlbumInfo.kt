package com.example.domain.entity.album

import com.example.domain.model.response.album.AlbumMemberInfo

data class AlbumInfo(
    val albumName: String = "기본 앨범",
    val albumThumbnail: String = "",
    val albumFounder: String = "",
    val albumFoundDate: String = "",
    val albumMemberMax: Int = 0,
    val albumMemberInfo: List<AlbumMemberInfo> = emptyList(),
    val albumPictureCount: Int = 0,
    val albumPictureCountFromCurrentUser: Int = 0,
    val trashCount: Int = 0,
    val currentUsage: Float = 0.0f,
    val totalUsage: Float = 0.0f
)