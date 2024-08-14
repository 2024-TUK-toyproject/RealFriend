package com.example.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CallLogResponse(
    val name: String?,
    val phone: String?,
    val duration: Int?,
    val count: Int?,
)
