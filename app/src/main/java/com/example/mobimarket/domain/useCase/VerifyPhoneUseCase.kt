package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.ProfileUpdate
import retrofit2.Response

interface VerifyPhoneUseCase {

    suspend fun verifyPhoneNumber(code: String,bearerToken:String): Response<ProfileUpdate>
}