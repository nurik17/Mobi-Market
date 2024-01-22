package com.example.mobimarket.di


import com.example.mobimarket.data.remote.MobiApi
import com.example.mobimarket.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @MobiMarketUrl
    fun provideMobiMarketUrl(): String {
        return Constant.BASE_URL
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @MobiMarketUrl
    @Provides
    @Singleton
    fun getMobiMarketRetrofit(@MobiMarketUrl url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getMobiApi(@MobiMarketUrl retrofit: Retrofit): MobiApi {
        return retrofit.create(MobiApi::class.java)
    }
}

@Qualifier
annotation class MobiMarketUrl