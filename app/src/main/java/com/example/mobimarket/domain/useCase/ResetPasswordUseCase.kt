package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.model.ResetPasswordResponse
import retrofit2.Response

interface ResetPasswordUseCase {

    suspend fun resetPasswordById(
        userId: Int,
        code: String,
        bearerToken: String
    ): Response<ResetPasswordResponse>

}