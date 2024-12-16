package com.example.data.network

import com.example.domain.model.request.ContactsRequest
import com.example.domain.model.response.user.MostCalledUsersResponse
import com.example.domain.DefaultResponse
import com.example.domain.model.login.CallLog
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.response.contact.CallLogResponse
import com.example.domain.model.response.user.FriendResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ContactApi {

    // 연락처 동기화
    @POST("/users/upload/phone")
    suspend fun syncContacts(@Body contactsRequest: ContactsRequest): DefaultResponse<Unit>

    // 통화기록 동기화
    @POST("/users/upload/callrecord")
    suspend fun syncCallLogs(@Body callLogRequest: ContentRequest<List<CallLog>>): DefaultResponse<List<CallLogResponse>>

    // 가장 많이 연락한 사람 통화 기록 조회(3명)
    @GET("/users/get/longestcall")
    suspend fun readMostCalledUsers(): DefaultResponse<MostCalledUsersResponse>

    // 연락처 리스트 조회
    @GET("/users/get/friend")
    suspend fun readAllFriends(): DefaultResponse<List<FriendResponse>>

}