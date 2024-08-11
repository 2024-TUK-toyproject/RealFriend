package com.example.connex.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ApiState
import com.example.domain.model.response.asDomain
import com.example.domain.usecase.ReadMostCallUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val readMostCallUsersUseCase: ReadMostCallUsersUseCase
): ViewModel() {
    var userId: Long = 0

    fun fetchReadMostCallUsers() {
        viewModelScope.launch {
            when (val result = readMostCallUsersUseCase.invoke(111111).first()) {
                is ApiState.Error -> Log.d("daeyoung", "Error: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.NotResponse -> Log.d("daeyoung", "NotResponse: ${result.message},\n ${result.exception}")
                is ApiState.Success -> {
                    val mostCalledDateTime = result.data.asDomain()
                    Log.d("daeyoung", "mostCalledDateTime: $mostCalledDateTime")
                }
            }

        }
    }
}