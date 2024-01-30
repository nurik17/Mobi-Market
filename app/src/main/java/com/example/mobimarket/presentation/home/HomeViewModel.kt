package com.example.mobimarket.presentation.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobimarket.data.model.Product
import com.example.mobimarket.data.source.remote.MobiApi
import com.example.mobimarket.domain.useCase.GetProductsUseCase
import com.example.mobimarket.presentation.home.adapter.ProductsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mobiApi: MobiApi,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    private val accessToken = sharedPreferences.getString("access_token",null)

    val getProducts: Flow<PagingData<Product>> =  Pager (
        config = PagingConfig(5, enablePlaceholders = true),
        pagingSourceFactory = { ProductsPagingSource(5,accessToken!!,mobiApi) }
    ).flow.cachedIn(viewModelScope)

}
