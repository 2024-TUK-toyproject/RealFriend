package com.example.domain.model.login

import kotlinx.serialization.Serializable

@Serializable
data class CallLog(
    val name: String?,
    val phone: String?,
    val duration: String?,
    val date: String?,
    val type: Int?
)