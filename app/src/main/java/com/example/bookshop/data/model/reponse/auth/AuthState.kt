package com.example.bookshop.data.model.reponse.auth
import com.example.bookshop.data.model.reponse.Error
import com.example.bookshop.data.model.response.auth.AuthResponse

data class AuthState(
    val error: Error,
    val loginResponse: AuthResponse?,
)