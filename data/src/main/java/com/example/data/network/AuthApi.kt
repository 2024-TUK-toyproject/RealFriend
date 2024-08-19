package com.example.data.network

import com.example.domain.DefaultResponse
import com.example.domain.model.ApiResponse
import com.example.domain.model.request.ContactsRequest
import com.example.domain.model.response.Token
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    //
    @POST("/login")
    suspend fun loginToken(): DefaultResponse<Token>
}