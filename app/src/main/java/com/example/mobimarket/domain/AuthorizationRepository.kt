package com.example.mobimarket.domain

import com.example.mobimarket.data.entity.LogoutResponse
import com.example.mobimarket.data.entity.RegisterResponse
import retrofit2.Response

interface AuthorizationRepository {

    suspend fun login(username: String, password: String): Response<LoginResponse>

    suspend fun registerCheck(
        username: String,
        email: String,
        confirm_password: String,
        password: String
    ): Response<RegisterResponse>

    suspend fun logout(refresh_token: String,accessToken: String): Response<LogoutResponse>
}