package com.example.mobimarket.data.entity

import com.example.mobimarket.domain.LoginResponse

sealed class LoginResult {
    data class Success(val loginResponse: LoginResponse?) : LoginResult()
    data class Error(val error: String) : LoginResult()
    object Loading: LoginResult()
}
