package com.example.domain.entity.contact

data class ContactInfo(
    val userId: Long,
    val name: String,
    val phone: String,
    val profileImage: String,
    val isExist: Boolean,
)
