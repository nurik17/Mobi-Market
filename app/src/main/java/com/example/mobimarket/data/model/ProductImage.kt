package com.example.mobimarket.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductImage(
    val id: Int,
    val image: String
): Parcelable