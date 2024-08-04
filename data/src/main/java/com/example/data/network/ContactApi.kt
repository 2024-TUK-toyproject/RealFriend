package com.example.data.network

import com.example.data.model.ApiResponse
import com.example.data.model.request.CertificateCodeRequest
import com.example.data.model.request.ContactsRequest
import com.example.data.model.response.UserIdResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ContactApi {

    // 연락처 동기화
    @POST("/users/upload/phone")
    suspend fun syncContacts(@Body contactsRequest: ContactsRequest): ApiResponse<Unit>
}