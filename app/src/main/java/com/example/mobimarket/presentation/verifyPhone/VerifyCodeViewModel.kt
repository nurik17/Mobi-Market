package com.example.mobimarket.presentation.verifyPhone

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.model.StateResult
import com.example.mobimarket.domain.useCase.VerifyPhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class VerifyCodeViewModel @Inject constructor(
    private val verifyPhoneUseCase: VerifyPhoneUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _verifyPhone = MutableLiveData<StateResult>()
    val verifyPhone: LiveData<StateResult> = _verifyPhone

    fun verifyPhoneCode(code: String) {
        viewModelScope.launch {
            _verifyPhone.postValue(StateResult.Loading)
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)
                val response = verifyPhoneUseCase.verifyPhoneNumber(code, bearerToken!!)
                if(response.isSuccessful){
                    _verifyPhone.postValue(StateResult.Success(response.body()))
                }else{
                    _verifyPhone.postValue(StateResult.Error(response.message()))
                }
            }catch (e: Exception){
                _verifyPhone.postValue(StateResult.Error(e.message.toString()))
            }
        }
    }

}