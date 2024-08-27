package com.example.data.di

import com.example.data.repository.ContactRepositoryImpl
import com.example.data.repository.FriendRepositoryImpl
import com.example.data.repository.LoginRepositoryImpl
import com.example.domain.repository.ContactRepository
import com.example.domain.repository.FriendRepository
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

    @Binds
    @ViewModelScoped
    abstract fun bindContactRepository(
        contactRepositoryImpl: ContactRepositoryImpl,
    ): ContactRepository

    @Binds
    @ViewModelScoped
    abstract fun bindFriendRepository(
        friendRepositoryImpl: FriendRepositoryImpl,
    ): FriendRepository
}