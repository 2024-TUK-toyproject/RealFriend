package com.example.data.network

import com.example.domain.DefaultResponse
import com.example.domain.model.request.ContactRequest
import com.example.domain.model.request.ContentRequest
import com.example.domain.model.request.FriendIdRequest
import com.example.domain.model.request.FriendRequestIdRequest
import com.example.domain.model.response.ContactResponse
import com.example.domain.model.response.FriendRequestResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface FriendApi {

    // 친구 요청 리스트 조회
    @GET("/users/get/friend/request")
    suspend fun readAllFriendRequest(): DefaultResponse<Map<String, List<FriendRequestResponse>>>

    // 연락처(앱 사용자, 미사용자) 리스트 조회
    @POST("/users/recommend/friends")
    suspend fun readAllContact(@Body contacts: ContentRequest<List<ContactRequest>>): DefaultResponse<List<ContactResponse>>

    // 친구 요청 수락
    @POST("/users/accept/friend")
    suspend fun acceptFriendRequest(@Body friendRequestIdRequest: FriendRequestIdRequest): DefaultResponse<Unit>

    // 친구 요청 거절
    @POST("/users/reject/friend")
    suspend fun rejectFriendRequest(): DefaultResponse<Unit>

    // 친구 추가
    @POST("/users/add/friend")
    suspend fun addFriend(@Body friendId: FriendIdRequest): DefaultResponse<Unit>

    // 친구 삭제
    @HTTP(method = "DELETE", path = "/users/delete/friend", hasBody = true)
    suspend fun deleteFriend(@Body contactId: ContentRequest<List<FriendIdRequest>>): DefaultResponse<Unit>
}