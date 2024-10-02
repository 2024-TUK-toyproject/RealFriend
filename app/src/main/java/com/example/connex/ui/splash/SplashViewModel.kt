package com.example.connex.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiState
import com.example.domain.usecase.AutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val autoLoginUseCase: AutoLoginUseCase) : ViewModel() {

    fun fetchAutoLogin(onSuccess: () -> Unit, onFail: () -> Unit, notResponse: () -> Unit) {
        viewModelScope.launch {
            when (val result = autoLoginUseCase().first()) {
                is ApiState.Error -> {
                    Log.d("daeyoung", "Error: ${result.errMsg}")
                    onFail()
                }
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "fetchAutoLogin NotResponse: ${result.message}\n${result.exception}")

                    if (result.exception is ConnectException) {
                        Log.d("daeyoung", "ConnectException: ${result.message}")
                        notResponse()
                    } else if (result.exception is java.net.SocketTimeoutException) {
                        Log.d("daeyoung", "SocketTimeoutException: ${result.message}")
                    }
                }
                is ApiState.Success -> onSuccess()
            }
        }
    }

}