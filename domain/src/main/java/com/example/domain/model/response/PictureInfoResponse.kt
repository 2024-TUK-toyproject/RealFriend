package com.example.domain.model.response

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

data class PictureInfo(
    val name: String = "",
    val date: String = "0000-00-00",
    val time: String = "00:00:00",
    val usage: Double = 0.0,
    val uploadUser: String = "user1",
    val uploadUserProfile: String = "https://bucketconnex.s3.amazonaws.com/profile/default_profile/default.png",
    val uploadDate: String = "0000-00-00",
    val uploadTime: String = "00:00:00"
)

fun PictureInfoResponse.asDomain() = PictureInfo(
    name = name ?: "",
    date = date ?: "0000-00-00",
    time = time ?: "00:00:00",
    usage = usage ?: 0.0,
    uploadUser = uploadUser ?: "user1",
    uploadUserProfile = uploadUserProfile ?: "https://bucketconnex.s3.amazonaws.com/profile/default_profile/default.png",
    uploadDate = uploadDate ?: "0000-00-00",
    uploadTime = uploadTime ?: "00:00:00"
)