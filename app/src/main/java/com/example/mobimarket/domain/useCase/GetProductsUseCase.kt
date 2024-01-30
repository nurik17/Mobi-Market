package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.model.Product
import com.example.mobimarket.data.model.ProductResponse
import retrofit2.Response

interface GetProductsUseCase {

    suspend fun getProducts(page: Int,limit: Int,bearerToken: String): List<Product>

}