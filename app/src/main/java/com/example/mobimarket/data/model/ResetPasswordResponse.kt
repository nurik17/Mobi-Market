package com.example.mobimarket.data.model

data class ResetPasswordResponse(
    val access: String,
    val message: String,
    val refresh: String
)