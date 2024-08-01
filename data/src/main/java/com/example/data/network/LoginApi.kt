package com.example.data.network

import com.example.data.model.ApiResponse
import com.example.data.model.UserIdDTO
import retrofit2.http.POST

interface LoginApi {

    // 인증번호 요청
    @POST("/register/users")
    fun requestCertificateCode(): ApiResponse<Unit>

    // 인증번호 요청
    @POST("/register/certificate")
    fun checkCertificateCode(): ApiResponse<UserIdDTO>
}