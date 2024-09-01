package com.example.data.repository

import com.example.data.network.ContactApi
import com.example.domain.model.ApiState
import com.example.domain.model.home.MostCalledDateTime
import com.example.domain.model.login.CallLog
import com.example.domain.model.login.Contact
import com.example.domain.model.request.ContactRequest
import com.example.domain.model.request.ContactsRequest
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.request.toDTO
import com.example.domain.model.response.CallLogResponse
import com.example.domain.model.response.FriendResponse
import com.example.domain.model.response.asDomain
import com.example.domain.model.safeFlow
import com.example.domain.model.safeFlow2
import com.example.domain.model.safeFlowUnit
import com.example.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactApi: ContactApi,
) : ContactRepository {
    override fun syncContacts(
        userId: Long,
        contacts: List<Contact>,
    ): Flow<ApiState<Unit>> = safeFlowUnit {
        val contactList = mutableListOf<ContactRequest>()
        contacts.forEach {
            contactList.add(it.toDTO())
        }
        val contactsRequest = ContactsRequest(userId.toString(), contactList)
        contactApi.syncContacts(contactsRequest)
    }


    override fun syncCallLogs(callLogs: List<CallLog>): Flow<ApiState<List<CallLogResponse>>> =
        safeFlow {
            contactApi.syncCallLogs(ContentRequest(callLogs))
        }


    override fun readMostCallUsers(): Flow<ApiState<MostCalledDateTime>> =
        safeFlow2(apiFunc = { contactApi.readMostCalledUsers() }) {
            it.asDomain()
        }

    override fun readAllFriends(): Flow<ApiState<List<FriendResponse>>> = safeFlow {
        contactApi.readAllFriends()
    }
}