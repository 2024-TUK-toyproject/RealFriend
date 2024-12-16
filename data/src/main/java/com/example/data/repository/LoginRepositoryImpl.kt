package com.example.data.repository

import com.example.data.datastore.TokenManager
import com.example.data.network.AuthApi
import com.example.data.network.LoginApi
import com.example.data.network.UserApi
import com.example.domain.model.ApiState
import com.example.domain.model.request.CertificateCodeRequest
import com.example.domain.model.request.PhoneRequest
import com.example.domain.model.response.user.CertificateCodeResponse
import com.example.domain.model.response.Token
import com.example.domain.model.response.user.UserInfo
import com.example.domain.model.response.user.toEntity
import com.example.domain.model.safeFlow
import com.example.domain.model.safeFlow2
import com.example.domain.model.safeFlowAndSaveToken
import com.example.domain.model.safeFlowUnit
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val authApi: AuthApi,
    private val userApi: UserApi,
    private val tokenManager: TokenManager,
) : LoginRepository {
    override fun checkAccessToken(): Flow<ApiState<Token>> = safeFlow {
        authApi.loginToken()
    }

    override fun requestCertificateCode(
        phone: String,
        mobileCarrier: String,
    ): Flow<ApiState<Unit>> = safeFlowUnit {
        loginApi.requestCertificateCode(PhoneRequest(phone, mobileCarrier))
    }

    override fun checkCertificateCode(
        phone: String,
        mobileCarrier: String,
        certificateCode: String,
    ): Flow<ApiState<CertificateCodeResponse>> = safeFlowAndSaveToken(
        apiFunc = {
            loginApi.checkCertificateCode(
                CertificateCodeRequest(
                    certificateCode,
                    phone,
                    mobileCarrier
                )
            )
        }
    ) { accessToken, refreshToken ->
        tokenManager.saveAccessToken(accessToken)
        tokenManager.saveRefreshToken(refreshToken)
    }

    override fun signupProfileImage(
        userId: Long,
        name: String,
        fcmToken: String,
        file: MultipartBody.Part,
    ): Flow<ApiState<Unit>> = safeFlowUnit {
        loginApi.signupProfileImage(userId.toString(), name, fcmToken, file)
    }

    override fun readUserInfo(): Flow<ApiState<UserInfo>> = safeFlow2(apiFunc = { userApi.readUserInfo() }) {
        it.toEntity()
    }
}