package com.example.domain.entity.album

data class CommentInfo (
    val replyId: String,
    val userName: String,
    val userProfile: String,
    val date: String,
    val time: String,
    val message: String
)