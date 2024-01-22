package com.example.mobimarket.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.LoginResult
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel(){

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    fun login(username: String,password: String){
        viewModelScope.launch{
            _loginResult.value = LoginResult.Loading
            try {
                val response = loginUseCase.login(username,password)
                if (response.isSuccessful){
                    _loginResult.value = LoginResult.Success(response.body())
                }
                else{
                    val errorMessage = parseErrorMessage(response)
                    _loginResult.value = LoginResult.Error(errorMessage)
                }
            }catch (_ : Exception){
                _loginResult.value = LoginResult.Error("An error occurred")
            }
        }
    }
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
