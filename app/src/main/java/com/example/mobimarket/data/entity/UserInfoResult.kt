package com.example.mobimarket.data.entity

import com.example.mobimarket.domain.User

sealed class UserInfoResult{
    data class Success(val data: User?) : UserInfoResult()
    data class Error(val error: String) : UserInfoResult()
    object Loading : UserInfoResult()
}
