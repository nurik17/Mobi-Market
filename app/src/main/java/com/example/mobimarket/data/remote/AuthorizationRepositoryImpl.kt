package com.example.mobimarket.data.remote

import com.example.mobimarket.data.entity.LoginRequestBody
import com.example.mobimarket.data.entity.RegisterResponse
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.AuthorizationRepository
import com.example.mobimarket.domain.RegisterRequestBody
import retrofit2.Response
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val api: MobiApi
) : AuthorizationRepository {
    override suspend fun login(username: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequestBody(username,password)
        return api.login(loginRequest)
    }

    override suspend fun registerCheck(
        username: String,
        email: String,
        confirm_password: String,
        password: String
    ): Response<RegisterResponse> {
        val registerRequest = RegisterRequestBody(confirm_password, email, password, username)
        return api.register(registerRequest)
    }
}
