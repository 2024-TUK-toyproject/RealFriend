package com.example.domain.usecase

import com.example.domain.model.ApiState
import com.example.domain.model.response.Token
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    operator fun invoke(): Flow<ApiState<Token>> =
        loginRepository.checkAccessToken()
}