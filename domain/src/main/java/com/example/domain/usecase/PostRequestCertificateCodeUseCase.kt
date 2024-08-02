package com.example.domain.usecase

import com.example.domain.repository.LoginRepository
import javax.inject.Inject

class PostRequestCertificateCodeUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(phone: String, mobileCarrier: String) = loginRepository.requestCertificateCode(phone, mobileCarrier)
}


