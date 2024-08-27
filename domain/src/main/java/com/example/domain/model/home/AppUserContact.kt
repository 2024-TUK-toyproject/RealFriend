package com.example.domain.model.home

data class AppUserContact(
    val userId: Long,
    val name: String,
    val phone: String,
    val isAppUsed: Boolean
)