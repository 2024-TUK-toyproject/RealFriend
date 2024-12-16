package com.example.domain.model.request

import com.example.domain.entity.contact.ExtractedCallLog
import kotlinx.serialization.Serializable

@Serializable
data class CallLogRequest(
    val content: List<ExtractedCallLog>
)
