package com.example.mobimarket.presentation.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _loginResult = MutableLiveData<StateResult>()
    val loginResult: LiveData<StateResult> = _loginResult

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
                    val errorMessage = parseErrorMessage(response)
                    _loginResult.postValue(StateResult.Error(errorMessage))
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



    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled
    private fun parseErrorMessage(response: Response<LoginResponse>): String {
        return when (response.code()) {
            404 -> "Неверный логин или пароль"
            else -> response.errorBody()?.string() ?: "Проверьте подключение к интернету"
        }
    }

    fun updateButtonState(userName: String, password: String) {
        _isButtonEnabled.value = userName.isNotEmpty() || password.isNotEmpty()
    }
}
