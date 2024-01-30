package com.example.mobimarket.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.domain.RegisterResponse
import com.example.mobimarket.data.model.StateResult
import com.example.mobimarket.domain.useCase.RegisterCheckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerCheckUseCase: RegisterCheckUseCase
) : ViewModel() {

    private val _registerCheck = MutableLiveData<StateResult>()
    val registerCheck: LiveData<StateResult> = _registerCheck

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    fun registerCheck(email: String, userName: String, password: String, confirm_password: String) {
        viewModelScope.launch {
            _registerCheck.postValue(StateResult.Loading)
            try {
                val response =
                    registerCheckUseCase.registerCheck(email, userName, password, confirm_password)
                if (response.isSuccessful) {
                    _registerCheck.postValue(StateResult.Success(response.body()))
                } else {
                    val errorMessage = parseErrorMessage(response)
                    _registerCheck.postValue(StateResult.Error(errorMessage))
                }
            } catch (e: Exception) {
                _registerCheck.postValue(StateResult.Error(e.message.toString()))
            }
        }
    }

    private fun parseErrorMessage(response: Response<RegisterResponse>): String {
        return if (!response.isSuccessful) {
            // Handle unsuccessful response here
            "Failed to register. Please try again."
        } else {
            val registerResponse = response.body()
            registerResponse?.error?.let { error ->
                val emailErrorList = error.email

                // Check if the list contains the specified error message
                if ("Enter a valid email address." in emailErrorList) {
                    return "Invalid email address. Please enter a valid email."
                } else {
                    // Handle other email errors or return a default message
                    return "Other email error occurred."
                }
            } ?: "Unknown error occurred."
        }
    }



    fun updateButtonStateRegister(userName: String, password: String) {
        _isButtonEnabled.value = userName.isNotEmpty() || password.isNotEmpty()
    }
}

