package com.example.mobimarket.data.remote

import com.example.mobimarket.domain.repository.MobiRepository
import javax.inject.Inject

class MobiRepositoryImpl @Inject constructor(
    private val api: MobiApi
) : MobiRepository {


}