package com.example.mobimarket.presentation.password

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.ForgotPasswordResponse
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.domain.useCase.ForgotPasswordGetCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordWritePhoneViewModel @Inject constructor(
    private val forgotPasswordGetCodeUseCase: ForgotPasswordGetCodeUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _forgotPassword = MutableLiveData<StateResult>()
    val forgotPassword: LiveData<StateResult> = _forgotPassword

    fun forgotPasswordGetCode(phone:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _forgotPassword.postValue(StateResult.Loading) //background thread
            try {
                val accessToken = sharedPreferences.getString("access_token",null)
                val response =
                    forgotPasswordGetCodeUseCase.forgotPasswordGetMessage(phone,accessToken!!)
                val userId = response.body()?.user_id
                saveUserId(userId.toString())
                if (response.isSuccessful) {
                    _forgotPassword.postValue(StateResult.Success(response.body()))
                } else {
                    _forgotPassword.postValue(StateResult.Error(response.message()))
                }
            } catch (e: Exception) {
                _forgotPassword.postValue(StateResult.Error(e.message.toString()))
            }
        }
    }

    private fun saveUserId(userId: String){
        val editor = sharedPreferences.edit()
        editor.putString("userId",userId)
        editor.apply()
    }
}