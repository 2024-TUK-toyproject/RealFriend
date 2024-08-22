package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.home.MostCalledDateTime
import com.example.domain.model.login.CallLog
import com.example.domain.model.login.Contact
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.response.CallLogResponse
import com.example.domain.model.response.ContactResponse
import com.example.domain.model.response.MostCalledDateTimeResponse
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun syncContacts(userId: Long, contacts: List<Contact>): Flow<ApiState<Unit>>

    fun syncCallLogs(userId: Long, callLogs: List<CallLog>): Flow<ApiState<List<CallLogResponse>>>

    fun readMostCallUsers(userId: Long): Flow<ApiState<MostCalledDateTimeResponse>>

    fun readAllFriends(): Flow<ApiState<List<ContactResponse>>>

    fun deleteFriend(friendIds: List<String>): Flow<ApiState<Unit>>

}