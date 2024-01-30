package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.model.Product
import com.example.mobimarket.data.model.ProductResponse
import com.example.mobimarket.domain.repository.MobiRepository
import retrofit2.Response
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val repository: MobiRepository
) : GetProductsUseCase {

    override suspend fun getProducts(
        page: Int,
        limit: Int,
        bearerToken: String
    ): List<Product> {
        return repository.getProducts(page, limit, bearerToken)
    }
}