package com.example.data.di

import com.example.data.network.ContactApi
import com.example.data.network.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideLoginApi(@NetworkModule.ConnexRetrofit retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    fun provideContactApi(@NetworkModule.ConnexRetrofit retrofit: Retrofit): ContactApi {
        return retrofit.create(ContactApi::class.java)
    }
}