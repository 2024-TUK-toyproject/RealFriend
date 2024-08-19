package com.example.domain.model.response

import com.example.domain.model.login.IsExistingUser
import kotlinx.serialization.Serializable

@Serializable
data class CertificateCodeResponse(
    val userId: String?,
    val isExist: Boolean?,
    val accessToken: String?,
    val refreshToken: String?,
)


fun CertificateCodeResponse.asDomain() = IsExistingUser(
    userId = userId?.toLong() ?: 0L,
    isExist = isExist ?: false
)