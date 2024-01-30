package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.model.ResetPasswordResponse
import com.example.mobimarket.domain.repository.AuthorizationRepository
import retrofit2.Response
import javax.inject.Inject

class ResetPasswordUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
):ResetPasswordUseCase {
    override suspend fun resetPasswordById(
        userId: Int,
        code: String,
        bearerToken: String
    ): Response<ResetPasswordResponse> {
        return repository.resetPasswordById(userId,code, bearerToken)
    }
}