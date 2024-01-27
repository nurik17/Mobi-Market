package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.ProfileUpdate
import retrofit2.Response

interface UpdateProfileInfoUseCase {

    suspend fun updateUserInfo(
        first_name: String,
        last_name: String,
        username: String,
        photo: String?,
        birth_date: String,
        email: String,
        bearerToken: String
    ): Response<ProfileUpdate>
}