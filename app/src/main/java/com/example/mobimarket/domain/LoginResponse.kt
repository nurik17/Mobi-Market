package com.example.mobimarket.domain

data class LoginResponse(
    val access: String,
    val birth_date: Any,
    val email: String,
    val first_name: String,
    val last_name: String,
    val phone: Any,
    val refresh: String,
    val username: String
)