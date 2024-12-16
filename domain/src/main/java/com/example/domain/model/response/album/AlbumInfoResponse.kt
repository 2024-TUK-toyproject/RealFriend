package com.example.domain.model.response.album

import com.example.domain.entity.album.AlbumInfo
import kotlinx.serialization.Serializable

@Serializable
data class AlbumInfoResponse(
    val albumName: String?,
    val albumMemberCount: Int?,
    val albumPictureCount: Int?,
    val currentUsage: Double?,
    val totalUsage: Double?
)

fun AlbumInfoResponse.toEntity() = AlbumInfo(
    albumName = albumName ?: "기본 앨범",
    albumMemberCount = albumMemberCount ?: 0,
    albumPictureCount = albumPictureCount ?: 0,
    currentUsage = currentUsage ?: 0.0,
    totalUsage = totalUsage ?: 0.0
)