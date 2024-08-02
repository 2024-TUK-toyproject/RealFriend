package com.example.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CertificateCodeRequest(
    val code: String,
    val phone: String,
    val company: String
)
