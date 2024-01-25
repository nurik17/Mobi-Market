package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.LoginResponse
import retrofit2.Response

interface LoginUseCase {

    suspend fun login(username: String, password: String): Response<LoginResponse>
}
