package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.login.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun syncContacts(userId: Long, contacts: List<Contact>): Flow<ApiState<Unit>>

}