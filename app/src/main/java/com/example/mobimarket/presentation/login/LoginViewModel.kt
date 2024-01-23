package com.example.mobimarket.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.LoginResult
import com.example.mobimarket.data.entity.RegisterResult
import com.example.mobimarket.domain.LoginResponse
import com.example.mobimarket.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        viewModelScope.launch(Dispatchers.IO){
            _loginResult.postValue(LoginResult.Loading) //background thread
            try {
                val response = loginUseCase.login(username,password)
                if (response.isSuccessful){
                    _loginResult.postValue(LoginResult.Success(response.body()))
                }
                else{
                    val errorMessage = parseErrorMessage(response)
                    _loginResult.postValue(LoginResult.Error(errorMessage))
                }
            }catch (e : Exception){
                _loginResult.postValue(LoginResult.Error(e.message.toString()))
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
