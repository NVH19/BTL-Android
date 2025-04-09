package com.example.bookshop.datasource

import com.example.bookshop.data.model.Customer
import com.example.bookshop.data.model.reponse.Message
import com.example.bookshop.data.model.response.auth.AuthResponse
import retrofit2.Response

interface IDataSource {
    suspend fun login(email: String, password: String): Response<AuthResponse>?
    suspend fun forgotPassword(email: String): Response<Message>
    suspend fun register(email: String, name: String, password: String): Response<AuthResponse>
    suspend fun getCustomer(): Response<Customer>?
}