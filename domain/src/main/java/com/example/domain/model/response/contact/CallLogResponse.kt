package com.example.domain.model.response.contact

import com.example.domain.entity.contact.CallLog
import kotlinx.serialization.Serializable

@Serializable
data class CallLogResponse(
    val name: String?,
    val phone: String?,
    val duration: Int?,
    val count: Int?,
)


fun CallLogResponse.toEntity() = CallLog(
    name = name ?: "",
    phone = phone ?: "",
    duration = duration ?: 0,
    count = count ?: 0,
)