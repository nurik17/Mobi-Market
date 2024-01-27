package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.repository.AuthorizationRepository
import com.example.mobimarket.domain.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
): LoginUseCase {
    override suspend fun login(username: String, password: String): Response<LoginResponse> {
        return repository.login(username,password)
    }
}