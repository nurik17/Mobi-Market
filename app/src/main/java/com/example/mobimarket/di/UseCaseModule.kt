package com.example.mobimarket.di

import com.example.mobimarket.domain.useCase.LoginUseCase
import com.example.mobimarket.domain.useCase.LoginUseCaseImpl
import com.example.mobimarket.domain.useCase.LogoutUseCase
import com.example.mobimarket.domain.useCase.LogoutUseCaseImpl
import com.example.mobimarket.domain.useCase.RegisterCheckUseCase
import com.example.mobimarket.domain.useCase.RegisterCheckUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule{

    @Binds
    fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds fun bindRegisterCheckUseCase(impl: RegisterCheckUseCaseImpl): RegisterCheckUseCase
    @Binds fun bindLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase

}