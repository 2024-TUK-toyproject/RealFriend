package com.example.data.repository

import com.example.data.model.request.ContactDTO
import com.example.data.model.request.ContactsRequest
import com.example.data.model.request.toDTO
import com.example.data.network.ContactApi
import com.example.domain.model.ApiState
import com.example.domain.model.login.Contact
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
}