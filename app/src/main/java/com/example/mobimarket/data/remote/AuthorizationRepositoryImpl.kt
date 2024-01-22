package com.example.mobimarket.data.remote

import com.example.mobimarket.data.entity.LoginRequestBody
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.AuthorizationRepository
import retrofit2.Response
import javax.inject.Inject

class AuthorizationRepositoryImpl @Inject constructor(
    private val api: MobiApi
) : AuthorizationRepository {
    override suspend fun login(username: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequestBody(username,password)
        return api.login(loginRequest)
    }
}
