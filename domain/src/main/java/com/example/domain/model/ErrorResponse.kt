package com.example.domain.model

data class ErrorResponse (
    val code: String,
    val isSuccess: Boolean,
    val message: String
)

