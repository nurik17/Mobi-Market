package com.example.mobimarket.presentation.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.model.Product
import com.example.mobimarket.data.model.Resource
import com.example.mobimarket.domain.useCase.GetProductInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val infoUseCase: GetProductInfoUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _productInfo = MutableStateFlow<Resource<Product>>(Resource.UnSpecified())
    val productInfo = _productInfo.asStateFlow()

    private val accessToken = sharedPreferences.getString("access_token", null)

    fun getProductInfoById(productId: Int) {
        viewModelScope.launch {
            _productInfo.value = Resource.Loading()
            try {
                val result = withContext(Dispatchers.IO) {
                    infoUseCase.getInfoProductById(productId, accessToken!!)
                }
                _productInfo.value = Resource.Success(result)
            } catch (e: Exception) {
                _productInfo.value = Resource.Error(e.message.toString())
            }
        }
    }
}