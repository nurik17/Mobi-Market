package com.example.mobimarket.domain

import retrofit2.Response

interface AuthorizationRepository {

    suspend fun login(username: String, password: String): Response<LoginResponse>
}