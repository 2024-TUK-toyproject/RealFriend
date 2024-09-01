package com.example.domain.model.response

import com.example.domain.model.home.MostCalledDateTime
import com.example.domain.model.home.MostCalledUser
import kotlinx.serialization.Serializable

@Serializable
data class MostCalledUserResponse(
    val name: String?,
    val phone: String?,
    val duration: String?,
//    val type: String?,
)

@Serializable
data class MostCalledDateTimeResponse(
    val date: String?,
    val updateTime: Int?,
    val difference: Int?,
    val users: List<MostCalledUserResponse>,
)


fun MostCalledUserResponse.asDomain() = MostCalledUser(
    name = name ?: "",
    phone = phone ?: "",
    duration = duration?.toInt() ?: 0,
)

fun MostCalledDateTimeResponse.asDomain() = MostCalledDateTime(
    date = date ?: "",
    updateTime = updateTime ?: 0,
    difference = difference ?: 0,
    user = users.map { it.asDomain() },
)