package com.example.mobimarket.domain.useCase

import com.example.mobimarket.domain.ProfileUpdate
import com.example.mobimarket.domain.repository.AuthorizationRepository
import com.example.mobimarket.domain.repository.MobiRepository
import retrofit2.Response
import javax.inject.Inject

class UpdateProfileInfoUseCaseImpl @Inject constructor(
    private val repository: AuthorizationRepository
) : UpdateProfileInfoUseCase {
    override suspend fun updateUserInfo(
        first_name: String,
        last_name: String,
        username: String,
        photo: String?,
        birth_date: String,
        email: String,
        bearerToken: String
    ): Response<ProfileUpdate> {
        return repository.updateUserInfo(
            first_name, last_name, username, photo,birth_date, email,bearerToken
        )
    }

}