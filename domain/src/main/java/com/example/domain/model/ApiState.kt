package com.example.domain.model

sealed class ApiState {
    data class Success<T>(val value: T): ApiState()
    object Loading: ApiState()
    data class Error(val errMsg: String): ApiState()
}