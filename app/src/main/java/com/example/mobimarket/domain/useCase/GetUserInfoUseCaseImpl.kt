package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.User
import com.example.mobimarket.domain.repository.AuthorizationRepository
import com.example.mobimarket.domain.repository.MobiRepository
import retrofit2.Response
import javax.inject.Inject

class GetUserInfoUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
) : GetUserInfoUseCase {
    override suspend fun getUserInfo(bearerToken: String): Response<User> {
        return repository.getInfoUser(bearerToken)
    }

}