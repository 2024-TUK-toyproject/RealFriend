package com.example.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ConnexOkhttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ConnexRetrofit

    @Provides
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
    }

    @ConnexOkhttpClient
    @Provides
    fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

//    @ConnexRetrofit
//    @Provides
//    fun provideRunwayRetrofit(
//        @ConnexOkhttpClient okHttpClient: OkHttpClient,
//        json: Json,
//    ): Retrofit {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(BuildConfig.BASE_URL)
//            .addConverterFactory(json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
//            .build()
//    }


}
