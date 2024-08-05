package com.example.connex.ui.login

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connex.utils.asMultipart
import com.example.domain.model.ApiState
import com.example.domain.model.UserId
import com.example.domain.model.login.MobileCarrier
import com.example.domain.model.login.Phone
import com.example.domain.usecase.CheckCertificateCodeUseCase
import com.example.domain.usecase.PostRequestCertificateCodeUseCase
import com.example.domain.usecase.SignupProfileImageUseCase
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
import javax.inject.Inject

data class LoginUiState(
    val phoneNumber: Phone = Phone.default(),
    val verificationCode: String = "",
)

data class ProfileInitUiState(
    val imageUrl: Uri = Uri.EMPTY,
    val name: String = "",
)

sealed class LoginScreenState() {
    data object Phone : LoginScreenState()
    data object MobileCarrier : LoginScreenState()
    data object CertificateCode : LoginScreenState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    val postRequestCertificateCodeUseCase: PostRequestCertificateCodeUseCase,
    val checkCertificateCodeUseCase: CheckCertificateCodeUseCase,
    val signupProfileImageUseCase: SignupProfileImageUseCase,
    @ApplicationContext val context: Context
) : ViewModel() {

    var userId = 0L

    private val _phone = MutableStateFlow(Phone.default())
    val phone: StateFlow<Phone> = _phone.asStateFlow()

    private val _verificationCode = MutableStateFlow(" ")
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

    fun fetchRequestCertificateCode(onSuccess: () -> Unit) {

        viewModelScope.launch {
            when (val result = postRequestCertificateCodeUseCase(
                phone = _phone.value.number,
                mobileCarrier = _phone.value.mobileCarrier.name
            ).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess { onSuccess() }
                is ApiState.NotResponse -> TODO()
            }
        }
    }

    fun fetchCheckCertificateCode(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = checkCertificateCodeUseCase(
                phone = _phone.value.number,
                mobileCarrier = _phone.value.mobileCarrier.name,
                certificateCode = _verificationCode.value
            ).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess {
                    userId = (it as UserId).userId
                    Log.d("daeyoung", "userId: $userId")
                    onSuccess()
                }

                is ApiState.NotResponse -> TODO()
            }
        }
    }

    fun fetchSignupProfileImage(onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = signupProfileImageUseCase(
                userId = userId,
                name = _name.value,
                file = imageUrl.value.asMultipart("file", context.contentResolver)!!
            ).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess { onSuccess() }
                is ApiState.NotResponse -> TODO()
            }
        }
    }


}