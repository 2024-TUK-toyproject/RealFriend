package com.example.connex.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MobileCarrier
import com.example.domain.model.Phone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class LoginPhoneAuthUiState(
    val phoneNumber: Phone = Phone.default(),
    val verificationCode: String = "",
)
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _phone = MutableStateFlow(Phone.default())
    val phone: StateFlow<Phone> = _phone.asStateFlow()

    private val _verificationCode = MutableStateFlow(" ")
    val verificationCode: StateFlow<String> = _verificationCode.asStateFlow()

    val loginPhoneAuthUiState = combine(_phone, _verificationCode) { phone, verificationCode ->
        LoginPhoneAuthUiState(phone, verificationCode)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LoginPhoneAuthUiState()
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

}