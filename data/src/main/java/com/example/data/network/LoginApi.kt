package com.example.data.network

import com.example.data.model.ApiResponse
import com.example.data.model.request.PhoneRequest
import com.example.data.model.response.PhoneResponse
import com.example.data.model.response.UserIdDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    // 인증번호 요청
    @POST("/register/users")
    suspend fun requestCertificateCode(@Body phone: PhoneRequest): ApiResponse<PhoneResponse>

    // 인증번호 요청
    @POST("/register/certificate")
    fun checkCertificateCode(): ApiResponse<UserIdDTO>
}