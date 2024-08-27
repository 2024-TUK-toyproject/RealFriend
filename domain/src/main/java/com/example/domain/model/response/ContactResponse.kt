package com.example.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ContactResponse (
    val userId: String?,
    val name: String?,
    val phone: String?,
    val profileImage: String?,
    val isFriend: Boolean?,
)

//data class Friend(
//    val userId: Long,
//    val name: String,
//    val phone: String,
//    val profileImage: String,
//    val isFriend: Boolean,
//)
//
//fun ContactResponse.asDomain() = Friend(
//    userId = userId?.toLong() ?: 0L,
//    name = name ?: "",
//    phone = phone ?: "",
//    profileImage = profileImage ?: "",
//    isFriend = isFriend ?: false,
//)

data class Friend(
    val userId: Long,
    val name: String,
    val phone: String,
    val profileImage: String,
    val isFriend: Boolean,
)

fun ContactResponse.asDomain() = Friend(
    userId = userId?.toLong() ?: 0L,
    name = name ?: "",
    phone = phone ?: "",
    profileImage = profileImage ?: "",
    isFriend = isFriend ?: false,
)