package com.example.domain.model.response.album

import com.example.domain.entity.album.PictureInfo
import kotlinx.serialization.Serializable

@Serializable
data class PictureInfoResponse(
    val name: String?,
    val date: String?,
    val time: String?,
    val usage: Double?,
    val uploadUser: String?,
    val uploadUserProfile: String?,
    val uploadDate: String?,
    val uploadTime: String?
)



fun PictureInfoResponse.asDomain() = PictureInfo(
    name = name ?: "",
    date = date ?: "0000-00-00",
    time = time ?: "00:00:00",
    usage = usage ?: 0.0,
    uploadUser = uploadUser ?: "user1",
    uploadUserProfile = uploadUserProfile ?: "",
    uploadDate = uploadDate ?: "0000-00-00",
    uploadTime = uploadTime ?: "00:00:00"
)