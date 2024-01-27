package com.example.mobimarket.presentation.profile

import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobimarket.data.entity.StateResult
import com.example.mobimarket.data.entity.UserInfoResult
import com.example.mobimarket.domain.useCase.GetUserInfoUseCase
import com.example.mobimarket.domain.useCase.UpdateProfileInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val userInfoUseCase: GetUserInfoUseCase,
    private val updateUseCase: UpdateProfileInfoUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _userInfo = MutableLiveData<UserInfoResult>()
    val userInfo: LiveData<UserInfoResult> = _userInfo

    private val _updateInfo = MutableLiveData<StateResult>()
    val updateInfo: LiveData<StateResult> = _updateInfo

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.postValue(UserInfoResult.Loading) //background thread
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)

                val response = userInfoUseCase.getUserInfo(bearerToken!!)
                if (response.isSuccessful) {
                    _userInfo.postValue(UserInfoResult.Success(response.body()))
                } else {
                    _userInfo.postValue(UserInfoResult.Error(response.errorBody().toString()))
                }
            } catch (e: Exception) {
                _userInfo.postValue(UserInfoResult.Error(e.message.toString()))
            }
        }
    }

    fun getUpdateInfo(
       first_name: String,
       last_name: String,
       username: String,
       photo: String?,
       birth_date: String,
       email: String
    ) {
        viewModelScope.launch {
            _updateInfo.postValue(StateResult.Loading)
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)
                val response = updateUseCase.updateUserInfo(
                    first_name, last_name, username,photo,birth_date, email,bearerToken!!
                )
                if(response.isSuccessful){
                    _updateInfo.postValue(StateResult.Success(response.body()))
                }else{
                    _updateInfo.postValue(response.body()?.message?.let { StateResult.Error(it) })
                }
            }catch (e: Exception){
                _updateInfo.postValue(StateResult.Error(e.message.toString()))
            }
        }
    }

    fun sendImage(image: Bitmap){
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG,70,stream)
        val byteArray = stream.toByteArray()
        val body = MultipartBody.Part.createFormData(
            "photo[content]","profilePhoto",
            byteArray.toRequestBody("image/*".toMediaTypeOrNull(),0,byteArray.size)
        )
    }
}