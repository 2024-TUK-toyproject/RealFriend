package com.example.domain.model.home

import kotlinx.serialization.Serializable


data class MostCalledUser(
    val name: String,
    val phone: String,
    val duration: Int,
)

data class MostCalledDateTime(
    val date: String,
    val updateTime: Int,
    val difference: Int,
    val user: List<MostCalledUser>,
)
