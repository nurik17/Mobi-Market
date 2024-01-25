package com.example.mobimarket.presentation.profile

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.LogoutResponse
import com.example.mobimarket.data.entity.LogoutResult
import com.example.mobimarket.domain.useCase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _logoutCase = MutableLiveData<LogoutResult>()
    val logoutCase: LiveData<LogoutResult> = _logoutCase

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            _logoutCase.postValue(LogoutResult.Loading) //background thread
            try {
                val refreshToken = sharedPreferences.getString("refresh_token", null)
                Log.d("ProfileViewModel", "refreshToken: $refreshToken")
                val accessToken = sharedPreferences.getString("access_token", null)
                Log.d("ProfileViewModel", "accessToken: $accessToken")

                val response = logoutUseCase.logout(refreshToken!!, accessToken!!)
                Log.d("ProfileViewModel", "response body: $response")
                if (response.isSuccessful) {
                    _logoutCase.postValue(LogoutResult.Success(response.body()))
                } else {
                    val errorMessage = parseErrorMessage(response)
                    _logoutCase.postValue(LogoutResult.Error(errorMessage))
                }
            } catch (e: Exception) {
                _logoutCase.postValue(LogoutResult.Error(e.message.toString()))
            }
        }
    }

    private fun parseErrorMessage(response: Response<LogoutResponse>): String {
        return when (response.code()) {
            400 -> "Unable to log out."
            else -> response.errorBody()?.string() ?: "Проверьте подключение к интернету"
        }
    }
}