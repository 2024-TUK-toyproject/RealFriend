package com.example.domain.usecase

import com.example.domain.model.ApiState
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRequestCertificateCodeUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(phone: String, mobileCarrier: String): Flow<ApiState<Unit>> =
        loginRepository.requestCertificateCode(phone, mobileCarrier)
}


