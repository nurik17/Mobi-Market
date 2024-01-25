package com.example.mobimarket.data.remote

import com.example.mobimarket.data.entity.LogoutBody
import com.example.mobimarket.data.entity.LoginRequestBody
import com.example.mobimarket.data.entity.LogoutResponse
import com.example.mobimarket.data.entity.RegisterResponse
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.RegisterRequestBody
import com.example.mobimarket.utils.Constant.X_CSRFToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

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

}