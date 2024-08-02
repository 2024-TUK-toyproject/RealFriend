package com.example.domain.repository

import com.example.domain.model.ApiState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun requestCertificateCode(phone: String, mobileCarrier: String): Flow<ApiState>
}