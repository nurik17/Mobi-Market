package com.example.mobimarket.domain.useCase

import com.example.mobimarket.data.model.Product

interface GetProductInfoUseCase {

    suspend fun getInfoProductById(id:Int,bearerToken: String): Product

}