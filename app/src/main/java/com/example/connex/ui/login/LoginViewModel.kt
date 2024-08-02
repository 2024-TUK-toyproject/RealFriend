package com.example.connex.ui.login

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiState
import com.example.domain.model.login.MobileCarrier
import com.example.domain.model.login.Phone
import com.example.domain.usecase.PostRequestCertificateCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class LoginViewModel @Inject constructor(
    val postRequestCertificateCodeUseCase: PostRequestCertificateCodeUseCase
) : ViewModel() {
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

    fun fetchRequestCertificateCode() {

        viewModelScope.launch {
            when (val result = postRequestCertificateCodeUseCase(
                phone = _phone.value.number,
                mobileCarrier = _phone.value.mobileCarrier.name
            ).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> Log.d("daeyoung", "api 통신 성공: ${result.value}")
            }
        }
    }


}