package com.example.mobimarket.data.source.remote

import com.example.mobimarket.data.model.Product
import com.example.mobimarket.domain.repository.MobiRepository
import javax.inject.Inject

class MobiRepositoryImpl @Inject constructor(
    private val api: MobiApi
) : MobiRepository {
    override suspend fun getInfoProductById(id: Int, bearerToken: String): Product {
        val accessToken = "$BEARER_PREFIX $bearerToken"
        return api.getInfoProductsById(id, accessToken)
    }

    override suspend fun getProducts(
        page: Int,
        limit: Int,
        bearerToken: String
    ): List<Product> {
        val accessToken = "$BEARER_PREFIX $bearerToken"
        return api.getProducts(page,limit,accessToken).results
    }

    companion object{
        private const val BEARER_PREFIX = "Bearer"
    }
}