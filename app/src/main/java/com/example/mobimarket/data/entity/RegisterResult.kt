package com.example.mobimarket.data.entity


sealed class RegisterResult {
    data class Success(val loginResponse: RegisterResponse?) : RegisterResult()
    data class Error(val error: String) : RegisterResult()
    object Loading: RegisterResult()
}
