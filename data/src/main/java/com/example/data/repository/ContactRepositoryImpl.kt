package com.example.data.repository

import com.example.data.network.ContactApi
import com.example.domain.entity.contact.CallLog
import com.example.domain.entity.contact.Contact
import com.example.domain.entity.contact.ExtractedCallLog
import com.example.domain.entity.user.Friend
import com.example.domain.entity.user.MostCalledUsers
import com.example.domain.model.ApiState
import com.example.domain.model.request.ContactRequest
import com.example.domain.model.request.ContactsRequest
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.request.toDTO
import com.example.domain.model.response.contact.toEntity
import com.example.domain.model.response.user.asDomain
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


    override fun syncCallLogs(callLogs: List<ExtractedCallLog>): Flow<ApiState<List<CallLog>>> =
        safeFlow2(apiFunc = { contactApi.syncCallLogs(ContentRequest(callLogs)) }) { callLogs ->
            callLogs.map { it.toEntity() }
        }


    override fun readMostCallUsers(): Flow<ApiState<MostCalledUsers>> =
        safeFlow2(apiFunc = { contactApi.readMostCalledUsers() }) { it.asDomain() }

    override fun readAllFriends(): Flow<ApiState<List<Friend>>> = safeFlow2(apiFunc = { contactApi.readAllFriends() }) { list -> list.map { it.asDomain() }}
}