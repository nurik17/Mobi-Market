package com.example.mobimarket.data.entity

import com.example.mobimarket.domain.LoginResponse

sealed class LogoutResult {
    data class Success(val loginResponse: LogoutResponse?) : LogoutResult()
    data class Error(val error: String) : LogoutResult()
    object Loading: LogoutResult()
}
