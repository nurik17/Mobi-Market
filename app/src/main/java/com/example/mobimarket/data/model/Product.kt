package com.example.mobimarket.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val images: List<ProductImage>,
    val short_description: String,
    val full_description: String,
    val user: Int,
    val like_count: Int,
    val liked_by_current_user: Boolean
): Parcelable