package com.example.data.di

import com.example.data.repository.LoginRepositoryImpl
import com.example.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl,
    ): LoginRepository
}