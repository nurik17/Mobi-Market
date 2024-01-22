package com.example.mobimarket.domain

import retrofit2.Response

interface LoginUseCase {

    suspend fun login(username: String, password: String): Response<LoginResponse>
}
