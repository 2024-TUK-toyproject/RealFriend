package com.example.data.repository

import com.example.data.network.ContactApi
import com.example.domain.model.ApiState
import com.example.domain.model.login.CallLog
import com.example.domain.model.login.Contact
import com.example.domain.model.request.CallLogRequest
import com.example.domain.model.request.ContactDTO
import com.example.domain.model.request.ContactsRequest
import com.example.domain.model.request.toDTO
import com.example.domain.model.response.CallLogResponse
import com.example.domain.model.response.ContactResponse
import com.example.domain.model.response.MostCalledDateTimeResponse
import com.example.domain.model.safeFlow
import com.example.domain.repository.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactApi: ContactApi
): ContactRepository {
    override fun syncContacts(
        userId: Long,
        contacts: List<Contact>
    ): Flow<ApiState<Unit>> = flow {
        kotlin.runCatching {
            val contactList = mutableListOf<ContactDTO>()
            contacts.forEach {
                contactList.add(it.toDTO())
            }
            val contactsRequest = ContactsRequest(userId.toString(), contactList)
            contactApi.syncContacts(contactsRequest)
        }.onSuccess {
            emit(ApiState.Success(Unit))
        }.onFailure {error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override fun syncCallLogs(userId: Long, callLogs: List<CallLog>): Flow<ApiState<List<CallLogResponse>>> = safeFlow {
        val callLogRequest = CallLogRequest(
            userId = userId.toString(),
            content = callLogs
        )
        contactApi.syncCallLogs(callLogRequest)
    }


    override fun readMostCallUsers(userId: Long): Flow<ApiState<MostCalledDateTimeResponse>> = safeFlow {
        contactApi.readMostCalledUsers(userId.toString())
    }

    override fun readAllFriends(): Flow<ApiState<List<ContactResponse>>> = safeFlow {
        contactApi.readAllFriends()
    }
}