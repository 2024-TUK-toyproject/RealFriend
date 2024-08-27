package com.example.domain.model

import com.example.domain.model.response.CertificateCodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T : Any> safeFlow(apiFunc: suspend () -> Response<ApiResponse<T>>): Flow<ApiState<T>> =
    flow {
        try {
            val res = apiFunc.invoke()
            if (res.isSuccessful) {
                emit(ApiState.Success(res.body()?.content ?: throw NullPointerException()))
            } else {
                val errorBody = res.errorBody() ?: throw NullPointerException()
                emit(ApiState.Error(errorBody.string()))
            }
        } catch (e: Exception) {
            emit(ApiState.NotResponse(message = e.message ?: "", exception = e))
        }
    }.flowOn(Dispatchers.IO)

fun <T : Any, D : Any> safeFlow2(apiFunc: suspend () -> Response<ApiResponse<T>>, onSuccess: (T) -> D): Flow<ApiState<D>> =
    flow {
        try {
            val res = apiFunc.invoke()

            if (res.isSuccessful) {
                emit(ApiState.Success(onSuccess(res.body()?.content ?: throw NullPointerException() )))
            } else {
                val errorBody = res.errorBody() ?: throw NullPointerException()
                emit(ApiState.Error(errorBody.string()))
            }
        } catch (e: Exception) {
            emit(ApiState.NotResponse(message = e.message ?: "", exception = e))
        }
    }.flowOn(Dispatchers.IO)

fun <T : Any> safeFlowUnit(apiFunc: suspend () -> Response<ApiResponse<T>>): Flow<ApiState<Unit>> =
    flow {
        try {
            val res = apiFunc.invoke()
            if (res.isSuccessful) {
                emit(ApiState.Success(Unit))
            } else {
                val errorBody = res.errorBody() ?: throw NullPointerException()
                emit(ApiState.Error(errorBody.string()))
            }
        } catch (e: Exception) {
            emit(ApiState.NotResponse(message = e.message ?: "", exception = e))
        }
    }.flowOn(Dispatchers.IO)



fun safeFlowAndSaveToken(apiFunc: suspend () -> Response<ApiResponse<CertificateCodeResponse>>, saveToken: suspend (String, String) -> Unit): Flow<ApiState<CertificateCodeResponse>> =
    flow {
        try {
            val res = apiFunc.invoke()
            if (res.isSuccessful) {
                val data = res.body()?.content
                saveToken(data?.accessToken ?: "", data?.refreshToken ?: "")
                emit(ApiState.Success(res.body()?.content ?: throw NullPointerException()))
            } else {
                val errorBody = res.errorBody() ?: throw NullPointerException()
                emit(ApiState.Error(errorBody.string()))
            }
        } catch (e: Exception) {
            emit(ApiState.NotResponse(message = e.message ?: "", exception = e))
        }
    }.flowOn(Dispatchers.IO)