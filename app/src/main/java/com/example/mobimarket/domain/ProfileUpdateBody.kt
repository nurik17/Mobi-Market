package com.example.mobimarket.domain

data class ProfileUpdateBody(
    val first_name: String,
    val last_name: String,
    val username: String,
    val photo: String?,
    val birth_date: String,
    val email: String
)