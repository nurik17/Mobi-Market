package com.example.mobimarket.domain.repository

import com.example.mobimarket.data.entity.ForgotPasswordResponse
import com.example.mobimarket.data.entity.ResetPasswordResponse
import com.example.mobimarket.domain.ProfileUpdate
import com.example.mobimarket.domain.LogoutResponse
import com.example.mobimarket.domain.RegisterResponse
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.ResponseAddNumber
import com.example.mobimarket.domain.User
import com.example.mobimarket.domain.VerifyPhoneBody
import retrofit2.Response

interface AuthorizationRepository {

    suspend fun login(username: String, password: String): Response<LoginResponse>

    suspend fun registerCheck(
        username: String,
        email: String,
        confirm_password: String,
        password: String
    ): Response<RegisterResponse>

    suspend fun logout(refresh_token: String, accessToken: String): Response<LogoutResponse>
    suspend fun getInfoUser(bearerToken: String): Response<User>

    suspend fun updateUserInfo(
        first_name: String,
        last_name: String,
        username: String,
        photo: String?,
        birth_date: String,
        email: String,
        bearerToken: String
    ): Response<ProfileUpdate>

    suspend fun verifyPhoneNumber(code: String, bearerToken: String): Response<ProfileUpdate>
    suspend fun addPhoneNumber(
        phoneNumber: String,
        bearerToken: String
    ): Response<ResponseAddNumber>

    suspend fun forgotPasswordGetMessage(
        phone: String,
        bearerToken: String
    ): Response<ForgotPasswordResponse>

    suspend fun resetPasswordById(
        userId: Int,
        code: String,
        bearerToken: String
    ): Response<ResetPasswordResponse>

    suspend fun changePassword(
        password: String,
        confirm_password: String,
        bearerToken: String
    ): Response<LogoutResponse>

}