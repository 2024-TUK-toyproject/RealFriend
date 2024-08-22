package com.example.domain

import com.example.domain.model.ApiResponse
import retrofit2.Response


typealias DefaultResponse<T> = Response<ApiResponse<T>>


