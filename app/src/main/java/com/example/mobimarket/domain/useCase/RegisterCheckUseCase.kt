package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.entity.RegisterResponse
import retrofit2.Response

interface RegisterCheckUseCase {

    suspend fun registerCheck(
        username: String,
        email: String,
        confirm_password: String,
        password: String
    ): Response<RegisterResponse>

}