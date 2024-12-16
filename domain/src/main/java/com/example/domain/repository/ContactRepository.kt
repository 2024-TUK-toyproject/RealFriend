package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.home.MostCalledUsers
import com.example.domain.model.login.CallLog
import com.example.domain.entity.contact.Contact
import com.example.domain.model.response.contact.CallLogResponse
import com.example.domain.model.response.Friend
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun syncContacts(userId: Long, contacts: List<Contact>): Flow<ApiState<Unit>>

    fun syncCallLogs(callLogs: List<CallLog>): Flow<ApiState<List<CallLogResponse>>>

    fun readMostCallUsers(): Flow<ApiState<MostCalledUsers>>

    fun readAllFriends(): Flow<ApiState<List<Friend>>>
}