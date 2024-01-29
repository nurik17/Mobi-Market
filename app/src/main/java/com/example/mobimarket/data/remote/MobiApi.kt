package com.example.mobimarket.data.remote

import com.example.mobimarket.data.entity.ForgotPasswordResponse
import com.example.mobimarket.data.entity.ResetPasswordResponse
import com.example.mobimarket.domain.AddPhoneNumber
import com.example.mobimarket.domain.ChangePasswordBody
import com.example.mobimarket.domain.LogoutBody
import com.example.mobimarket.domain.LoginRequestBody
import com.example.mobimarket.domain.LogoutResponse
import com.example.mobimarket.domain.ProfileUpdate
import com.example.mobimarket.domain.VerifyPhoneBody
import com.example.mobimarket.domain.RegisterResponse
import com.example.mobimarket.domain.User
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.ProfileUpdateBody
import com.example.mobimarket.domain.RegisterRequestBody
import com.example.mobimarket.domain.ResponseAddNumber
import com.example.mobimarket.utils.Constant.X_CSRFToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MobiApi {

    @Headers("X-CSRFToken: $X_CSRFToken")
    @POST("users/login/")
    suspend fun login(@Body request: LoginRequestBody): Response<LoginResponse>

    @Headers("X-CSRFToken: $X_CSRFToken")
    @POST("users/register/")
    suspend fun register(@Body request: RegisterRequestBody): Response<RegisterResponse>

    @Headers("X-CSRFToken: $X_CSRFToken")
    @POST("users/logout/")
    suspend fun logout(
        @Body refresh: LogoutBody,
        @Header("Authorization") token: String
    ): Response<LogoutResponse>

    @Headers("X-CSRFToken: $X_CSRFToken")
    @GET("users/me/")
    suspend fun getUserInfo(
        @Header("Authorization") bearerToken: String
    ) : Response<User>

    @Headers("X-CSRFToken: $X_CSRFToken")
    @PUT("users/profile/update/")
    suspend fun updateProfileInfo(
        @Body updateInfo: ProfileUpdateBody,
        @Header("Authorization") bearerToken: String
    ): Response<ProfileUpdate>

    @Headers("X-CSRFToken: $X_CSRFToken")
    @POST("users/verify-phone/")
    suspend fun verifyPhoneNumber(
        @Body code: VerifyPhoneBody,
        @Header("Authorization") bearerToken: String
    ): Response<ProfileUpdate>

    @Headers("X-CSRFToken: $X_CSRFToken")
    @PUT("users/add-phone/")
    suspend fun addPhoneNumber(
        @Body code: AddPhoneNumber,
        @Header("Authorization") bearerToken: String
    ): Response<ResponseAddNumber>

    @POST("users/forgot-password/")
    suspend fun forgotPasswordGetCode(
        @Body code: AddPhoneNumber,
        @Header("Authorization") bearerToken: String
    ): Response<ForgotPasswordResponse>

    @POST("users/reset-password/{user_id}/")
    suspend fun resetPasswordById(
        @Path("user_id") userId: Int,
        @Body code: VerifyPhoneBody,
        @Header("Authorization") bearerToken: String
    ): Response<ResetPasswordResponse>

    @POST("users/change-password/")
    suspend fun changePassword(
        @Body password: ChangePasswordBody,
        @Header("Authorization") bearerToken: String
    ): Response<LogoutResponse>
}