package com.example.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class PhoneRequest(
    val phone: String,
    val company: String
)