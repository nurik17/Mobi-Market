package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.LogoutResponse
import com.example.mobimarket.domain.repository.AuthorizationRepository
import retrofit2.Response
import javax.inject.Inject

class ChangePasswordUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
): ChangePasswordUseCase{
    override suspend fun changePassword(
        password: String,
        confirm_password: String,
        bearerToken: String
    ): Response<LogoutResponse> {
        return repository.changePassword(password,confirm_password,bearerToken)
    }
}