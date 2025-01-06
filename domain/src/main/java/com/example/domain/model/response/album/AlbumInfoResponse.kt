package com.example.domain.model.response.album

import com.example.domain.entity.album.AlbumInfo
import kotlinx.serialization.Serializable

@Serializable
data class AlbumInfoResponse(
    val albumName: String?,
    val albumThumbnail: String?,
    val albumFounder: String?,
    val albumFoundate: String?,
    val albumMemberMax: Int?,
    val albumMemberInfo: List<AlbumMemberInfo>?,
    val albumPictureCount: Int?,
    val albumPictureCountFromCurrentUser: Int?,
    val trashCount: Int?,
    val currentUsage: Float?,
    val totalUsage: Float?
)

@Serializable
data class AlbumMemberInfo(
    val userId: String,
    val userName: String,
    val userProfile: String,
    val pictureCount: Int,
    val authority: String
)

fun AlbumInfoResponse.toEntity() = AlbumInfo(
    albumName = albumName ?: "",
    albumThumbnail = albumThumbnail ?: "",
    albumFounder = albumFounder ?: "",
    albumFoundDate = albumFoundate ?: "",
    albumMemberMax = albumMemberMax ?: 0,
    albumMemberInfo = albumMemberInfo ?: emptyList(),
    albumPictureCount = albumPictureCount ?: 0,
    albumPictureCountFromCurrentUser = albumPictureCountFromCurrentUser ?: 0,
    trashCount = trashCount ?: 0,
    currentUsage = currentUsage ?: 0.0f,
    totalUsage = totalUsage ?: 0.0f
)