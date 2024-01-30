package com.example.mobimarket.data.source.remote

import com.example.mobimarket.data.model.ForgotPasswordResponse
import com.example.mobimarket.data.model.ResetPasswordResponse
import com.example.mobimarket.domain.AddPhoneNumber
import com.example.mobimarket.domain.ChangePasswordBody
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
        val loginRequest = LoginRequestBody(username, password)
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

    override suspend fun logout(
        refresh_token: String,
        accessToken: String
    ): Response<LogoutResponse> {
        val refreshToken = LogoutBody(refresh_token)
        val access = "$BEARER_PREFIX $accessToken"
        return api.logout(refreshToken, access)
    }

    override suspend fun getInfoUser(bearerToken: String): Response<User> {
        val access = "$BEARER_PREFIX $bearerToken"
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
        val updateBody =
            ProfileUpdateBody(first_name, last_name, username, photo, birth_date, email)
        val access = "$BEARER_PREFIX $bearerToken"
        return api.updateProfileInfo(updateBody, access)
    }

    override suspend fun verifyPhoneNumber(
        code: String,
        bearerToken: String
    ): Response<ProfileUpdate> {
        val verifyPhoneBody = VerifyPhoneBody(code)
        val access = "$BEARER_PREFIX $bearerToken"
        return api.verifyPhoneNumber(verifyPhoneBody, access)
    }

    override suspend fun addPhoneNumber(
        phoneNumber: String,
        bearerToken: String
    ): Response<ResponseAddNumber> {
        val phoneNumberRequest = AddPhoneNumber(phoneNumber)
        val access = "$BEARER_PREFIX $bearerToken"
        return api.addPhoneNumber(phoneNumberRequest, access)
    }

    override suspend fun forgotPasswordGetMessage(
        phone: String,
        bearerToken: String
    ): Response<ForgotPasswordResponse> {
        val phoneNumber = AddPhoneNumber(phone)
        val access = "$BEARER_PREFIX $bearerToken"
        return api.forgotPasswordGetCode(phoneNumber, access)
    }

    override suspend fun resetPasswordById(
        userId: Int,
        code: String,
        bearerToken: String
    ): Response<ResetPasswordResponse> {
        val message = VerifyPhoneBody(code)
        val access = "$BEARER_PREFIX $bearerToken"
        return api.resetPasswordById(userId, message, access)
    }

    override suspend fun changePassword(
        password: String,
        confirm_password: String,
        bearerToken: String
    ): Response<LogoutResponse> {
        val requestBody = ChangePasswordBody(password, confirm_password)
        val access = "$BEARER_PREFIX $bearerToken"
        return api.changePassword(requestBody, access)
    }
    companion object{
        private const val BEARER_PREFIX="Bearer"
    }
}

