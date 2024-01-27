package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.LogoutResponse
import com.example.mobimarket.domain.repository.AuthorizationRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
): LogoutUseCase {
    override suspend fun logout(token: String, refresh_token:String): Response<LogoutResponse> {
        return repository.logout(token,refresh_token)
    }
}