package com.example.domain.model.home

import kotlinx.serialization.Serializable


data class MostCalledUser(
    val name: String,
    val phone: String,
    val duration: String,
)

data class MostCalledDateTime(
    val date: String,
    val time: String,
    val difference: String,
    val user: List<MostCalledUser>,
)
