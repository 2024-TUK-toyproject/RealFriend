package com.example.domain.entity.contact

import kotlinx.serialization.Serializable

@Serializable
data class ExtractedCallLog(
    val name: String?,
    val phone: String?,
    val duration: String?,
    val date: String?,
    val type: Int?
)