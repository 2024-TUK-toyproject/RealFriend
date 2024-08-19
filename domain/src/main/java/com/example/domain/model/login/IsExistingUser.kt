package com.example.domain.model.login

data class IsExistingUser(
    val userId: Long,
    val isExist: Boolean,
)
