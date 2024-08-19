package com.example.connex.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiState
import com.example.domain.usecase.AutoLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val autoLoginUseCase: AutoLoginUseCase) : ViewModel() {

    fun fetchAutoLogin(onSuccess: () -> Unit, onFail: () -> Unit) {
        viewModelScope.launch {
            when (val result = autoLoginUseCase().first()) {
                is ApiState.Error -> onFail()
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> Log.d("daeyoung", "fetchAutoLogin NotResponse: ${result.message}\n${result.exception}")
                is ApiState.Success -> onSuccess()
            }
        }
    }

}