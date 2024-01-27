package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.ProfileUpdate
import com.example.mobimarket.domain.repository.AuthorizationRepository
import com.example.mobimarket.domain.repository.MobiRepository
import retrofit2.Response
import javax.inject.Inject

class VerifyPhoneUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
) : VerifyPhoneUseCase {
    override suspend fun verifyPhoneNumber(
        code: String,
        bearerToken: String
    ): Response<ProfileUpdate> {
        return repository.verifyPhoneNumber(code,bearerToken)
    }
}