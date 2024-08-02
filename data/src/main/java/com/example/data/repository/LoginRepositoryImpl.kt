package com.example.data.repository

import com.example.data.model.request.CertificateCodeRequest
import com.example.data.model.request.PhoneRequest
import com.example.data.model.response.UserIdResponse
import com.example.data.model.response.asDomain
import com.example.data.network.LoginApi
import com.example.domain.model.ApiState
import com.example.domain.model.UserId
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi): LoginRepository {
    override fun requestCertificateCode(phone: String, mobileCarrier: String): Flow<ApiState<Unit>> = flow {
        kotlin.runCatching {
            val phoneRequest = PhoneRequest(phone, mobileCarrier)
            loginApi.requestCertificateCode(phoneRequest).content
        }.onSuccess {
            emit(ApiState.Success(Unit))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

    override fun checkCertificateCode(
        phone: String,
        mobileCarrier: String,
        certificateCode: String
    ): Flow<ApiState<UserId>>  = flow {
        kotlin.runCatching {
            val certificateCodeRequest = CertificateCodeRequest(certificateCode, phone, mobileCarrier)
            val userIdResponse = loginApi.checkCertificateCode(certificateCodeRequest).content as UserIdResponse
            userIdResponse.asDomain()
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)
}