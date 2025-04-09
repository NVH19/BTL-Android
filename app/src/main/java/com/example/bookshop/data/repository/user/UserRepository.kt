package com.example.bookshop.data.repository.user

import com.example.bookshop.data.model.response.auth.AuthResponse
import com.example.bookshop.data.model.reponse.Message
import retrofit2.Response

interface UserRepository {
    suspend fun login(email: String, password: String): Response<AuthResponse>?
    suspend fun forgotPassword(email: String): Response<Message>
    suspend fun register(email: String, name: String, password: String): Response<AuthResponse>
}