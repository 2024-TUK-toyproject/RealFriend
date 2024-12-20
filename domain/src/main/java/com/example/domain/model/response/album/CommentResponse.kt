package com.example.domain.model.response.album

import com.example.domain.entity.album.CommentInfo
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    val replyKey: String?,
    val userName: String?,
    val userProfile: String?,
    val date: String?,
    val time: String?,
    val message: String?
)

fun CommentResponse.toEntity() = CommentInfo(
    replyId = replyKey ?: "",
    userName = userName ?: "",
    userProfile = userProfile ?: "",
    date = date ?: "",
    time = time ?: "",
    message = message ?: ""
)