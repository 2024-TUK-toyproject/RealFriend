package com.example.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PictureInfoResponse(
    val name: String,
    val usage: Int,
    val date: String,
    val time: String,
    val uploadName: String,
    val profileUrl: String
)

