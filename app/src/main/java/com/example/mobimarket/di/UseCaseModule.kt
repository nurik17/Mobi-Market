package com.example.mobimarket.di

import com.example.mobimarket.domain.LoginUseCase
import com.example.mobimarket.domain.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule{

    @Binds
    fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

}