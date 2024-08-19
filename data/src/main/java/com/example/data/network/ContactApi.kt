package com.example.data.network

import com.example.domain.model.request.ContactsRequest
import com.example.domain.model.response.MostCalledDateTimeResponse
import com.example.domain.DefaultResponse
import com.example.domain.model.ApiResponse
import com.example.domain.model.request.CallLogRequest
import com.example.domain.model.response.CallLogResponse
import com.example.domain.model.response.ContactResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContactApi {

    // 연락처 동기화
    @POST("/users/upload/phone")
    suspend fun syncContacts(@Body contactsRequest: ContactsRequest): ApiResponse<Unit>

    // 통화기록 동기화
    @POST("/users/upload/callrecord")
    suspend fun syncCallLogs(@Body callLogRequest: CallLogRequest): DefaultResponse<List<CallLogResponse>>

    // 가장 많이 연락한 사람 통화 기록 조회(3명)
    @GET("/users/get/{user_id}/longestcall")
    suspend fun readMostCalledUsers(@Path("user_id") userId: String): DefaultResponse<MostCalledDateTimeResponse>

    @GET("/users/get/friend")
    suspend fun readAllFriends(): DefaultResponse<List<ContactResponse>>

}