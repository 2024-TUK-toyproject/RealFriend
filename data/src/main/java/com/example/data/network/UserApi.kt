package com.example.data.network

import com.example.domain.DefaultResponse
import com.example.domain.model.response.user.UserInfoResponse
import retrofit2.http.GET

interface UserApi {
    @GET("/users/getprofile")
    suspend fun readUserInfo(): DefaultResponse<UserInfoResponse>
}