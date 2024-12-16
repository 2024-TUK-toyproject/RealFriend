package com.example.domain.repository

import com.example.domain.entity.contact.CallLog
import com.example.domain.entity.contact.Contact
import com.example.domain.entity.contact.ExtractedCallLog
import com.example.domain.entity.user.Friend
import com.example.domain.entity.user.MostCalledUsers
import com.example.domain.model.ApiState
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun syncContacts(userId: Long, contacts: List<Contact>): Flow<ApiState<Unit>>

    fun syncCallLogs(callLogs: List<ExtractedCallLog>): Flow<ApiState<List<CallLog>>>

    fun readMostCallUsers(): Flow<ApiState<MostCalledUsers>>

    fun readAllFriends(): Flow<ApiState<List<Friend>>>
}