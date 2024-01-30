package com.example.mobimarket.presentation.password

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.model.StateResult
import com.example.mobimarket.domain.useCase.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordVerifyCodeViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val sharedPreferences: SharedPreferences
): ViewModel(){

    private val _resetPasswordById = MutableLiveData<StateResult>()
    val resetPasswordById: LiveData<StateResult> = _resetPasswordById

    fun verifyCode(code: String,userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _resetPasswordById.postValue(StateResult.Loading) //background thread
            try {
                val accessToken = sharedPreferences.getString("access_token",null)
                val response =
                    resetPasswordUseCase.resetPasswordById(userId,code,accessToken!!)
                if (response.isSuccessful) {
                    _resetPasswordById.postValue(StateResult.Success(response.body()?.message))
                } else {
                    _resetPasswordById.postValue(StateResult.Error(response.message()))
                }
            } catch (e: Exception) {
                _resetPasswordById.postValue(StateResult.Error(e.message.toString()))
            }
        }
    }
}
