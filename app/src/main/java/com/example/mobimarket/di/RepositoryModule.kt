package com.example.mobimarket.di

import com.example.mobimarket.data.source.remote.AuthorizationRepositoryImpl
import com.example.mobimarket.data.source.remote.MobiRepositoryImpl
import com.example.mobimarket.domain.repository.AuthorizationRepository
import com.example.mobimarket.domain.repository.MobiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule{

    @Binds
    fun bindAuthorizationRepository(impl: AuthorizationRepositoryImpl): AuthorizationRepository

    @Binds
    fun bindMobiRepository(impl: MobiRepositoryImpl): MobiRepository
}