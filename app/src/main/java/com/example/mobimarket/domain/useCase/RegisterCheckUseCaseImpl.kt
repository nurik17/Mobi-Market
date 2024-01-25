package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.entity.RegisterResponse
import com.example.mobimarket.domain.AuthorizationRepository
import retrofit2.Response
import javax.inject.Inject

class RegisterCheckUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
): RegisterCheckUseCase {

    override suspend fun registerCheck(
        username: String,
        email: String,
        confirm_password: String,
        password: String
    ): Response<RegisterResponse> {
        return repository.registerCheck(username, email, confirm_password, password)
    }


}