package com.example.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ContactResponse(
    val userId: String?,
    val name: String?,
    val phone: String?,
    val profileImage: String?,
    val isExist: Boolean?,
)


data class ContactInfo(
    val userId: Long,
    val name: String,
    val phone: String,
    val profileImage: String,
    val isExist: Boolean,
)

fun ContactResponse.asDomain() = ContactInfo(
    userId = userId?.toLong() ?: 0L,
    name = name ?: "",
    phone = phone ?: "",
    profileImage = profileImage ?: "https://shpbucket.s3.amazonaws.com/profile/default_profile/default.png",
    isExist = isExist ?: false,
)