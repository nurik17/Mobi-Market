package com.example.mobimarket.presentation.authorization

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _loginResult = MutableLiveData<StateResult>()
    val loginResult: LiveData<StateResult> = _loginResult

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginResult.postValue(StateResult.Loading) //background thread
            try {
                val response = loginUseCase.login(username, password)
                if (response.isSuccessful) {
                    val refreshToken = response.body()?.refresh
                    val accessToken = response.body()?.access
                    accessToken?.let { refreshToken?.let { it1 -> saveRefreshToken(it1, it) } }
                    _loginResult.postValue(StateResult.Success(response.body()))
                } else {
                    _loginResult.postValue(StateResult.Error(response.message()))
                }
            } catch (e: Exception) {
                _loginResult.postValue(StateResult.Error(e.message.toString()))
            }
        }
    }
    private fun saveRefreshToken(refreshToken: String, token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("refresh_token", refreshToken)
        editor.putString("access_token", token)
        editor.apply()
    }

    fun updateButtonState(userName: String, password: String) {
        _isButtonEnabled.value = userName.isNotEmpty() || password.isNotEmpty()
    }
}
