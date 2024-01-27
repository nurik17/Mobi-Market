package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.User
import retrofit2.Response

interface GetUserInfoUseCase {

    suspend fun getUserInfo(bearerToken: String): Response<User>
}