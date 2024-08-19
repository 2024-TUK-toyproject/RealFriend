package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.UserId
import com.example.domain.model.response.CertificateCodeResponse
import com.example.domain.model.response.Token
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface LoginRepository {

    fun checkAccessToken(): Flow<ApiState<Token>>
    fun requestCertificateCode(phone: String, mobileCarrier: String): Flow<ApiState<Unit>>
    fun checkCertificateCode(phone: String, mobileCarrier: String, certificateCode: String): Flow<ApiState<CertificateCodeResponse>>

    fun signupProfileImage(userId: Long, name: String, file: MultipartBody.Part): Flow<ApiState<Unit>>

}