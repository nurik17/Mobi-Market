package com.example.mobimarket.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class User(
    val birth_date:  @RawValue Any?,
    val email: String,
    val first_name: String,
    val last_name: String,
    val phone: String,
    val photo: @RawValue Any?,
    val username: String
): Parcelable