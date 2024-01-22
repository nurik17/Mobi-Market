package com.example.mobimarket.domain

import retrofit2.Response
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
): LoginUseCase{
    override suspend fun login(username: String, password: String): Response<LoginResponse> {
        return repository.login(username,password)
    }
}