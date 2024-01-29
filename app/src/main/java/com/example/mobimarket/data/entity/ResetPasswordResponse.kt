package com.example.mobimarket.data.entity

data class ResetPasswordResponse(
    val access: String,
    val message: String,
    val refresh: String
)