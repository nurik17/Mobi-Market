package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.entity.LogoutResponse
import retrofit2.Response

interface LogoutUseCase {

    suspend fun logout(token: String,refresh_token: String): Response<LogoutResponse>
}