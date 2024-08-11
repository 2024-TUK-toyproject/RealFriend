package com.example.domain.model.request

import com.example.domain.model.login.Contact
import kotlinx.serialization.Serializable

@Serializable
data class ContactsRequest (
    val userId: String,
    val content: List<ContactDTO>
)

@Serializable
data class ContactDTO(
    val name: String,
    val phone: String
)

fun Contact.toDTO() = ContactDTO(
    name = name,
    phone = phone
)