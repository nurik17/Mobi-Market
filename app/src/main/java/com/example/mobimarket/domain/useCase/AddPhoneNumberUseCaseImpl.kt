package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.ResponseAddNumber
import com.example.mobimarket.domain.repository.AuthorizationRepository
import com.example.mobimarket.domain.repository.MobiRepository
import retrofit2.Response
import javax.inject.Inject

class AddPhoneNumberUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
): AddPhoneNumberUseCase {
    override suspend fun addPhoneNumber(
        phoneNumber: String,
        bearerToken: String
    ): Response<ResponseAddNumber> {
        return repository.addPhoneNumber(phoneNumber,bearerToken)
    }
}