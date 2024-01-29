package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.entity.ForgotPasswordResponse
import com.example.mobimarket.domain.repository.AuthorizationRepository
import retrofit2.Response
import javax.inject.Inject

class ForgotPasswordGetCodeUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
): ForgotPasswordGetCodeUseCase{
    override suspend fun forgotPasswordGetMessage(
        phone: String,
        bearerToken: String
    ): Response<ForgotPasswordResponse> {
        return repository.forgotPasswordGetMessage(phone, bearerToken)
    }

}