package com.example.mobimarket.domain

data class ChangePasswordBody(
    val confirm_password: String,
    val password: String
)