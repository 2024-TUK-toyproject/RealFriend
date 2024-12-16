package com.example.domain.model.response.user

import com.example.domain.entity.user.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse (
    val name: String?,
    val phone: String?,
    val profileImage: String?
)



fun UserInfoResponse.toEntity() = UserInfo(
    name = name ?: "null",
    phone = phone ?: "null",
    profileImage = profileImage ?: ""
)