package com.example.mobimarket.data.remote

import com.example.mobimarket.domain.AddPhoneNumber
import com.example.mobimarket.domain.LoginRequestBody
import com.example.mobimarket.domain.LogoutBody
import com.example.mobimarket.domain.ProfileUpdate
import com.example.mobimarket.domain.VerifyPhoneBody
import com.example.mobimarket.domain.LogoutResponse
import com.example.mobimarket.domain.RegisterResponse
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.ProfileUpdateBody
import com.example.mobimarket.domain.repository.AuthorizationRepository
import com.example.mobimarket.domain.RegisterRequestBody
import com.example.mobimarket.domain.ResponseAddNumber
import com.example.mobimarket.domain.User
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

    override suspend fun logout(refresh_token: String, accessToken: String): Response<LogoutResponse> {
        val refreshToken = LogoutBody(refresh_token)
        val access = "Bearer $accessToken"
        return api.logout(refreshToken,access)
    }

    override suspend fun getInfoUser(bearerToken: String): Response<User> {
        val access = "Bearer $bearerToken"
        return api.getUserInfo(access)
    }

    override suspend fun updateUserInfo(
        first_name: String,
        last_name: String,
        username: String,
        photo: String?,
        birth_date: String,
        email: String,
        bearerToken: String
    ): Response<ProfileUpdate> {
        val updateBody = ProfileUpdateBody(first_name, last_name, username, photo,birth_date, email)
        val access = "Bearer $bearerToken"
        return api.updateProfileInfo(updateBody,access)
    }

    override suspend fun verifyPhoneNumber(
        code: String,
        bearerToken: String
    ): Response<ProfileUpdate> {
        val verifyPhoneBody = VerifyPhoneBody(code)
        val access = "Bearer $bearerToken"
        return api.verifyPhoneNumber(verifyPhoneBody,access)
    }

    override suspend fun addPhoneNumber(
        phoneNumber: String,
        bearerToken: String
    ): Response<ResponseAddNumber> {
        val addPhoneNumber = AddPhoneNumber(phoneNumber)
        val access = "Bearer $bearerToken"
        return api.addPhoneNumber(addPhoneNumber,access)
    }
}
