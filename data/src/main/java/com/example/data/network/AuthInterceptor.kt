package com.example.data.network

import android.util.Log
import com.example.data.datastore.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    private val AUTHORIZATION = "Authorization"
    private val NETWORK_ERROR = 401

    override fun intercept(chain: Interceptor.Chain): Response {
//        runBlocking {
//            tokenManager.deleteAccessToken()
//        }

        val token: String = runBlocking {
            tokenManager.getAccessToken().first()
        } ?: return errorResponse(chain.request())

        Log.d("daeyoung", "token: $token")

//        val request = chain.request().newBuilder().header(AUTHORIZATION, "Bearer token").build()
        val request = chain.request().newBuilder().header(AUTHORIZATION, "$token").build()

        val response = chain.proceed(request)

        if (response.code == HTTP_OK) {
            val newAccessToken: String = response.header(AUTHORIZATION, null) ?: return response
//            Timber.d("new Access Token = ${newAccessToken}")

            CoroutineScope(Dispatchers.IO).launch {
                val existedAccessToken = tokenManager.getAccessToken().first()
                if (existedAccessToken != newAccessToken) {
                    tokenManager.saveAccessToken(newAccessToken)
//                    Timber.d("newAccessToken = ${newAccessToken}\nExistedAccessToken = ${existedAccessToken}")
                }
            }
        } else {
//            Timber.e("${response.code} : ${response.request} \n ${response.message}")
        }

        return response
    }

    private fun errorResponse(request: Request): Response = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(NETWORK_ERROR)
        .message("엑세스 토큰 없음")
        .body(ResponseBody.create(null, ""))
        .build()


}