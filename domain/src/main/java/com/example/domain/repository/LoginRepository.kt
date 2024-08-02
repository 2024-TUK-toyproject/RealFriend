package com.example.domain.repository

import com.example.domain.model.ApiState
import com.example.domain.model.UserId
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun requestCertificateCode(phone: String, mobileCarrier: String): Flow<ApiState<Unit>>
    fun checkCertificateCode(phone: String, mobileCarrier: String, certificateCode: String): Flow<ApiState<UserId>>


}