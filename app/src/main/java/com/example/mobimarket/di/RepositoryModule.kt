package com.example.mobimarket.di

import com.example.mobimarket.data.remote.AuthorizationRepositoryImpl
import com.example.mobimarket.domain.AuthorizationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule{

    @Binds
    fun bindProductRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository


}