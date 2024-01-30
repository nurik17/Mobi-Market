package com.example.mobimarket.data.model

data class ProductResponse(
    val page: Int,
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Product>
)
