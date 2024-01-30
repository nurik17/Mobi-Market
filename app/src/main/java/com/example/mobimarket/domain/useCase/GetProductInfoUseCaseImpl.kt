package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.model.Product
import com.example.mobimarket.domain.repository.MobiRepository
import javax.inject.Inject

class GetProductInfoUseCaseImpl @Inject constructor(
    private val repository: MobiRepository
): GetProductInfoUseCase {
    override suspend fun getInfoProductById(id: Int, bearerToken: String): Product {
        return repository.getInfoProductById(id,bearerToken)
    }
}