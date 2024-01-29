package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.entity.ForgotPasswordResponse
import retrofit2.Response

interface ForgotPasswordGetCodeUseCase {

    suspend fun forgotPasswordGetMessage(
        phone: String,
        bearerToken: String
    ): Response<ForgotPasswordResponse>

}