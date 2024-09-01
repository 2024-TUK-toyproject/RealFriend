package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.home.MostCalledDateTime
import com.example.domain.model.login.CallLog
import com.example.domain.model.login.Contact
import com.example.domain.model.response.CallLogResponse
import com.example.domain.model.response.FriendResponse
import com.example.domain.model.response.MostCalledDateTimeResponse
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun syncContacts(userId: Long, contacts: List<Contact>): Flow<ApiState<Unit>>

    fun syncCallLogs(callLogs: List<CallLog>): Flow<ApiState<List<CallLogResponse>>>

    fun readMostCallUsers(): Flow<ApiState<MostCalledDateTime>>

    fun readAllFriends(): Flow<ApiState<List<FriendResponse>>>
}