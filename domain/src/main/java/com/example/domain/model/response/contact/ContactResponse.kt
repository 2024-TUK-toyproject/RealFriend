package com.example.domain.model.response.contact

import com.example.domain.entity.contact.ContactInfo
import kotlinx.serialization.Serializable

@Serializable
data class ContactResponse(
    val userId: String?,
    val name: String?,
    val phone: String?,
    val profileImage: String?,
    val isExist: Boolean?,
)

fun ContactResponse.asDomain() = ContactInfo(
    userId = userId?.toLong() ?: 0L,
    name = name ?: "",
    phone = phone ?: "",
    profileImage = profileImage ?: "",
    isExist = isExist ?: false,
)