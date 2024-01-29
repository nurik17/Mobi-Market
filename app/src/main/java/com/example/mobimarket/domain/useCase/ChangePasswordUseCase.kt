package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.LogoutResponse
import retrofit2.Response

interface ChangePasswordUseCase {

    suspend fun changePassword(
        password: String,
        confirm_password: String,
        bearerToken: String
    ): Response<LogoutResponse>
}