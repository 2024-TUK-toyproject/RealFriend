package com.example.data.network

import com.example.domain.DefaultResponse
import com.example.domain.model.ApiResponse
import com.example.domain.model.request.CertificateCodeRequest
import com.example.domain.model.request.PhoneRequest
import com.example.domain.model.response.CertificateCodeResponse
import com.example.domain.model.response.UserIdResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface LoginApi {

    // 인증번호 요청
    @POST("/register/users")
    suspend fun requestCertificateCode(@Body phone: PhoneRequest): DefaultResponse<Unit>

    // 인증번호 요청
    @POST("/register/certificate")
    suspend fun checkCertificateCode(@Body certificateCode: CertificateCodeRequest): DefaultResponse<CertificateCodeResponse>

    // 초기 프로필 등록
    @Multipart
    @POST("/register/setprofile")
    suspend fun signupProfileImage(
        @Query("userId") userId: String,
        @Query("name") name: String,
        @Part file: MultipartBody.Part
    ): DefaultResponse<Unit>


}