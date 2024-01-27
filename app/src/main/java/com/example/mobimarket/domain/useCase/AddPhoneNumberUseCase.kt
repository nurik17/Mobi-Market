package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.ResponseAddNumber
import retrofit2.Response

interface AddPhoneNumberUseCase {

    suspend fun addPhoneNumber(phoneNumber: String,bearerToken: String): Response<ResponseAddNumber>

}