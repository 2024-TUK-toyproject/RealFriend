package com.example.domain.model.response

import com.example.domain.model.home.MostCalledDateTime
import com.example.domain.model.home.MostCalledUser
import kotlinx.serialization.Serializable

@Serializable
data class MostCalledUserResponse(
    val name: String?,
    val phone: String?,
    val duration: String?,
    val type: String?,
)

@Serializable
data class MostCalledDateTimeResponse(
    val date: String?,
    val time: Int?,
    val difference: Int?,
    val users: List<MostCalledUserResponse>,
)


fun MostCalledUserResponse.asDomain() = MostCalledUser(
    name = name ?: "",
    phone = phone ?: "",
    duration = duration ?: "",
)

fun MostCalledDateTimeResponse.asDomain() = MostCalledDateTime(
    date = date ?: "",
    time = time.toString(),
    difference = difference.toString(),
    user = users.map { it.asDomain() },
)