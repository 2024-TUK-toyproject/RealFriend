package com.example.domain.model.response.user

import com.example.domain.entity.user.Friend
import kotlinx.serialization.Serializable

@Serializable
data class FriendResponse (
    val userId: String?,
    val name: String?,
    val phone: String?,
    val profileImage: String?,
    val isFriend: Boolean?,
)

fun FriendResponse.asDomain() = Friend(
    userId = userId?.toLong() ?: 0L,
    name = name ?: "",
    phone = phone ?: "",
    profileImage = profileImage ?: "",
    isFriend = isFriend ?: false,
)