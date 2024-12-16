package com.example.connex.ui.login

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.utils.asMultipart
import com.example.connex.utils.formatPhoneDashNumber
import com.example.data.datastore.TokenManager
import com.example.domain.model.ApiState
import com.example.domain.entity.contact.MobileCarrier
import com.example.domain.entity.contact.Phone
import com.example.domain.model.response.user.CertificateCodeResponse
import com.example.domain.model.response.notification.asDomain
import com.example.domain.usecase.CheckCertificateCodeUseCase
import com.example.domain.usecase.PostRequestCertificateCodeUseCase
import com.example.domain.usecase.user.SignupProfileImageUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

data class LoginUiState(
    val phoneNumber: Phone = Phone.default(),
    val verificationCode: String = "",
)

data class ProfileInitUiState(
    val imageUrl: Uri = Uri.EMPTY,
    val name: String = "",
)

sealed class LoginScreenState(open val title: String) {
    data class Phone(override val title: String = "휴대전화 번호를\n입력해 주세요") : LoginScreenState(title)
    data class MobileCarrier(override val title: String = "통신사를\n선택해 주세요") : LoginScreenState(title)
    data class CertificateCode(override val title: String = "안중번호를\n입력해 주세요") :
        LoginScreenState(title)
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    val postRequestCertificateCodeUseCase: PostRequestCertificateCodeUseCase,
    val checkCertificateCodeUseCase: CheckCertificateCodeUseCase,
    val signupProfileImageUseCase: SignupProfileImageUseCase,
    val tokenManager: TokenManager,
    @ApplicationContext val context: Context,
) : ViewModel() {

    var userId = 0L

    private val _phone = MutableStateFlow(Phone.default())
    val phone: StateFlow<Phone> = _phone.asStateFlow()

    private val _verificationCode = MutableStateFlow("")
    val verificationCode: StateFlow<String> = _verificationCode.asStateFlow()

    private val _imageUrl = MutableStateFlow<Uri>(Uri.EMPTY)
    val imageUrl: StateFlow<Uri> = _imageUrl

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    val loginUiState = combine(_phone, _verificationCode) { phone, verificationCode ->
        LoginUiState(phone, verificationCode)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoginUiState()
    )

    val profileInitUiState = combine(_imageUrl, _name) { imageUrl, name ->
        ProfileInitUiState(imageUrl, name)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileInitUiState()
    )

    fun updatePhone(phone: String) {
        _phone.value = _phone.value.copy(number = phone)
    }

    fun updateMobileCarrier(mobileCarrier: MobileCarrier) {
        _phone.value = _phone.value.copy(mobileCarrier = mobileCarrier)
    }

    fun updateVerificationCode(code: String) {
        _verificationCode.value = code
    }

    fun updateImageUrl(imageUrl: Uri) {
        _imageUrl.value = imageUrl
    }

    fun updateName(name: String) {
        _name.value = name
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("daeyoung", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            } else {
                viewModelScope.launch {
                    val token = task.result
                    tokenManager.saveFCMToken(token)
                    Log.d("daeyoung", "FCMToken: $token")
                }
            }
        })
    }


    fun fetchRequestCertificateCode(onSuccess: () -> Unit, notResponse: () -> Unit) {

        viewModelScope.launch {
            when (val result = postRequestCertificateCodeUseCase(
                phone = phone.value.number.formatPhoneDashNumber(),
                mobileCarrier = phone.value.mobileCarrier.name
            ).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess { onSuccess() }
                is ApiState.NotResponse -> {
                    if (result.exception is ConnectException) {
                        notResponse()
                    }
                }
            }
        }
    }

    fun fetchCheckCertificateCode(onSuccess: (Boolean) -> Unit, notResponse: () -> Unit) {
        viewModelScope.launch {
            when (val result = checkCertificateCodeUseCase(
                phone = phone.value.number.formatPhoneDashNumber(),
                mobileCarrier = phone.value.mobileCarrier.name,
                certificateCode = verificationCode.value
            ).first()) {
                is ApiState.Error -> Log.d("daeyoung", "ApiState.Error: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess {
                    val isExist = (it as CertificateCodeResponse).asDomain()
                    userId = isExist.userId
                    Log.d("daeyoung", "userId: $userId")
                    onSuccess(isExist.isExist)
                    getFCMToken()
                }

                is ApiState.NotResponse -> {
                    Log.d(
                        "daeyoung",
                        "ApiState.NotResponse: ${result.message}\n${result.exception}"
                    )
                    if (result.exception is ConnectException) {
                        notResponse()
                    }
                }
            }
        }
    }

    fun fetchSignupProfileImage(onSuccess: () -> Unit, notResponse: () -> Unit) {
        viewModelScope.launch {
            tokenManager.getFCMToken().first()?.let { FCMToken ->
                when (val result = signupProfileImageUseCase(
                    userId = userId,
                    name = name.value,
                    fcmToken = FCMToken,
                    file = imageUrl.value.asMultipart("file", "", context.contentResolver)!!
                ).first()) {
                    is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                    ApiState.Loading -> TODO()
                    is ApiState.Success<*> -> result.onSuccess { onSuccess() }
                    is ApiState.NotResponse -> {
                        if (result.exception is ConnectException) {
                            notResponse()
                        }
                    }
                }
            }
        }
    }


}
