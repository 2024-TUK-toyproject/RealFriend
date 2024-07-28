package com.example.connex.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.domain.model.MobileCarrier
import com.example.domain.model.Phone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _phone = MutableStateFlow(Phone.default())
    val phone: StateFlow<Phone> = _phone.asStateFlow()

    fun updatePhone(phone: String) {
        _phone.value = _phone.value.copy(number = phone)
    }

    fun updateMobileCarrier(mobileCarrier: MobileCarrier) {
        _phone.value = _phone.value.copy(mobileCarrier = mobileCarrier)
    }

}