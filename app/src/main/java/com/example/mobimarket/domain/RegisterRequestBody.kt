package com.example.mobimarket.domain

data class RegisterRequestBody(
    val confirm_password: String,
    val email: String,
    val password: String,
    val username: String
)