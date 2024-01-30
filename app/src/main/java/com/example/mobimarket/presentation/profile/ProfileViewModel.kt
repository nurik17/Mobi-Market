package com.example.mobimarket.presentation.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.domain.LogoutResponse
import com.example.mobimarket.data.model.StateResult
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

    private val _logoutCase = MutableLiveData<StateResult>()
    val logoutCase: LiveData<StateResult> = _logoutCase

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            _logoutCase.postValue(StateResult.Loading) //background thread
            try {
                val refreshToken = sharedPreferences.getString("refresh_token", null)
                val accessToken = sharedPreferences.getString("access_token", null)

                val response = logoutUseCase.logout(refreshToken!!, accessToken!!)
                if (response.isSuccessful) {
                    _logoutCase.postValue(StateResult.Success(response.body()))
                } else {
                    val errorMessage = parseErrorMessage(response)
                    _logoutCase.postValue(StateResult.Error(errorMessage))
                }
            } catch (e: Exception) {
                _logoutCase.postValue(StateResult.Error(e.message.toString()))
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