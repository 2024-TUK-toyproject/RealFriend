package com.example.domain.model.request

import com.example.domain.entity.contact.Contact
import kotlinx.serialization.Serializable

@Serializable
data class ContactsRequest (
    val userId: String,
    val content: List<ContactRequest>
)

@Serializable
data class ContactRequest(
    val name: String,
    val phone: String
)

fun Contact.toDTO() = ContactRequest(
    name = name,
    phone = phone
)