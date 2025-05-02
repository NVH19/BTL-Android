package com.example.bookshop.data.model.response.auth
import com.example.bookshop.data.model.response.Error

data class AuthState(
    val error: Error,
    val loginResponse: AuthResponse?,
)