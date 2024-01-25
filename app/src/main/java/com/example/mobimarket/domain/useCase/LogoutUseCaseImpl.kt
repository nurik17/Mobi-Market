package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.entity.LogoutResponse
import com.example.mobimarket.domain.AuthorizationRepository
import retrofit2.Response
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
): LogoutUseCase {
    override suspend fun logout(token: String, refresh_token:String): Response<LogoutResponse> {
        return repository.logout(token,refresh_token)
    }
}