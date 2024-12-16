package com.example.domain.model.response.user

import com.example.domain.entity.user.MostCalledUser
import com.example.domain.entity.user.MostCalledUsers
import kotlinx.serialization.Serializable

@Serializable
data class MostCalledUserResponse(
    val name: String?,
    val phone: String?,
    val duration: String?,
//    val type: String?,
)

@Serializable
data class MostCalledUsersResponse(
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

fun MostCalledUsersResponse.asDomain() = MostCalledUsers(
    date = date ?: "",
    updateTime = updateTime ?: 0,
    difference = difference ?: 0,
    user = users.map { it.asDomain() },
)