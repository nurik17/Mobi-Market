package com.example.mobimarket.presentation.verifyPhone

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.domain.useCase.AddPhoneNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WritePhoneViewModel @Inject constructor(
    private val addPhoneNumberUseCase: AddPhoneNumberUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _addNumber = MutableLiveData<StateResult>()
    val addNumber: LiveData<StateResult> = _addNumber

    fun addPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            _addNumber.postValue(StateResult.Loading)
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)
                val response = addPhoneNumberUseCase.addPhoneNumber(
                    phoneNumber, bearerToken = bearerToken!!
                )
                if (response.isSuccessful) {
                    _addNumber.postValue(StateResult.Success(response.body()))
                }else{
                    _addNumber.postValue(StateResult.Error(response.message()))
                }
            }catch (e: Exception){
                _addNumber.postValue(StateResult.Error(e.message.toString()))
            }
        }
    }

}