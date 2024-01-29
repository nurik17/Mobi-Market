package com.example.mobimarket.presentation.password

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.domain.useCase.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _changePasswordCase = MutableLiveData<StateResult>()
    val changePasswordCase: LiveData<StateResult> = _changePasswordCase

    fun changePassword(password: String, confirm_password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _changePasswordCase.postValue(StateResult.Loading) //background thread
            try {
                val accessToken = sharedPreferences.getString("access_token", null)
                val response =
                    changePasswordUseCase.changePassword(password, confirm_password, accessToken!!)
                if (response.isSuccessful) {
                    _changePasswordCase.postValue(StateResult.Success(response.body()?.message))
                } else {
                    _changePasswordCase.postValue(StateResult.Error(response.message()))
                }
            } catch (e: Exception) {
                _changePasswordCase.postValue(StateResult.Error(e.message.toString()))
            }
        }
    }
}