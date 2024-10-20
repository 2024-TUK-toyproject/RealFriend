package com.example.domain.model.response.album

import com.example.domain.model.response.Friend
import com.example.domain.model.response.FriendResponse
import kotlinx.serialization.Serializable

@Serializable
data class AlbumInfoResponse(
    val albumName: String?,
    val albumMemberCount: Int?,
    val albumPictureCount: Int?,
    val currentUsage: Double?
)


data class AlbumInfo(
    val albumName: String = "기본 앨범",
    val albumMemberCount: Int = 1,
    val albumPictureCount: Int = 0,
    val currentUsage: Double = 0.0
)



fun AlbumInfoResponse.asDomain() = AlbumInfo(
    albumName = albumName ?: "기본 앨범",
    albumMemberCount = albumMemberCount ?: 0,
    albumPictureCount = albumPictureCount ?: 0,
    currentUsage = currentUsage ?: 0.0
)