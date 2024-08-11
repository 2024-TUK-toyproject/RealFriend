package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.home.MostCalledDateTime
import com.example.domain.model.login.Contact
import com.example.domain.model.response.MostCalledDateTimeResponse
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun syncContacts(userId: Long, contacts: List<Contact>): Flow<ApiState<Unit>>

    fun readMostCallUsers(userId: Long): Flow<ApiState<MostCalledDateTimeResponse>>

}