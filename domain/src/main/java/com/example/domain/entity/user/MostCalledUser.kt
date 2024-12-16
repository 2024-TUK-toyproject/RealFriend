package com.example.domain.entity.user

data class MostCalledUser(
    val name: String,
    val phone: String,
    val duration: Int,
)

data class MostCalledUsers(
    val date: String,
    val updateTime: Int,
    val difference: Int,
    val user: List<MostCalledUser>,
)
