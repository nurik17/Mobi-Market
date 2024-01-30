package com.example.mobimarket.domain.repository

import com.example.mobimarket.data.model.Product
import com.example.mobimarket.data.model.ProductResponse
import retrofit2.Response

interface MobiRepository {

    suspend fun getProducts(page: Int,limit: Int,bearerToken: String): List<Product>

    suspend fun getInfoProductById(id:Int,bearerToken: String): Product
}