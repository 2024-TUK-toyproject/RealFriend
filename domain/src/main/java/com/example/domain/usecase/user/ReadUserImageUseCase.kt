package com.example.domain.usecase.user

import com.example.domain.model.ApiState
import com.example.domain.model.response.user.UserInfo
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUserImageUseCase @Inject constructor(private val userInfoRepository: LoginRepository) {
    operator fun invoke(): Flow<ApiState<UserInfo>>
            = userInfoRepository.readUserInfo()
}