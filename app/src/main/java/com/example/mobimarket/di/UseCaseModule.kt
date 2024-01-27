package com.example.mobimarket.di

import com.example.mobimarket.domain.useCase.AddPhoneNumberUseCase
import com.example.mobimarket.domain.useCase.AddPhoneNumberUseCaseImpl
import com.example.mobimarket.domain.useCase.GetUserInfoUseCase
import com.example.mobimarket.domain.useCase.GetUserInfoUseCaseImpl
import com.example.mobimarket.domain.useCase.LoginUseCase
import com.example.mobimarket.domain.useCase.LoginUseCaseImpl
import com.example.mobimarket.domain.useCase.LogoutUseCase
import com.example.mobimarket.domain.useCase.LogoutUseCaseImpl
import com.example.mobimarket.domain.useCase.RegisterCheckUseCase
import com.example.mobimarket.domain.useCase.RegisterCheckUseCaseImpl
import com.example.mobimarket.domain.useCase.UpdateProfileInfoUseCase
import com.example.mobimarket.domain.useCase.UpdateProfileInfoUseCaseImpl
import com.example.mobimarket.domain.useCase.VerifyPhoneUseCase
import com.example.mobimarket.domain.useCase.VerifyPhoneUseCaseImpl
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

    @Binds fun bindUserInfoUseCase(impl: GetUserInfoUseCaseImpl): GetUserInfoUseCase

    @Binds fun bindUpdateUseCase(impl: UpdateProfileInfoUseCaseImpl): UpdateProfileInfoUseCase
    @Binds fun bindVerifyPhoneUseCase(impl: VerifyPhoneUseCaseImpl): VerifyPhoneUseCase
    @Binds fun bindAddPhoneUseCase(impl: AddPhoneNumberUseCaseImpl): AddPhoneNumberUseCase


}