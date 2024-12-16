package com.example.domain.usecase

import com.example.domain.model.ApiState
import com.example.domain.model.response.user.CertificateCodeResponse
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckCertificateCodeUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(
        phone: String,
        mobileCarrier: String,
        certificateCode: String
    ): Flow<ApiState<CertificateCodeResponse>> =
        loginRepository.checkCertificateCode(
            phone = phone,
            mobileCarrier = mobileCarrier,
            certificateCode = certificateCode
        )
}