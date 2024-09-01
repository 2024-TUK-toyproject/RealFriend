package com.example.domain.model.request

import com.example.domain.model.login.CallLog
import kotlinx.serialization.Serializable

@Serializable
data class CallLogRequest(
    val content: List<CallLog>
)
