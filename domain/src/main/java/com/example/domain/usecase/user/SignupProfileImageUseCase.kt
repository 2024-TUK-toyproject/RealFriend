package com.example.domain.usecase.user

import com.example.domain.repository.LoginRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class SignupProfileImageUseCase @Inject constructor(val loginRepository: LoginRepository) {
    operator fun invoke(userId: Long, name: String, fcmToken: String, file: MultipartBody.Part) =
        loginRepository.signupProfileImage(userId, name, fcmToken, file)
}