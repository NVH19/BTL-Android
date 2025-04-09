package com.example.bookshop.datasource

import com.example.bookshop.data.api.RetrofitClient
import com.example.bookshop.data.model.Customer
import com.example.bookshop.data.model.reponse.Message
import com.example.bookshop.data.model.response.auth.AuthResponse
import retrofit2.Response

class RemoteDataSource() : IDataSource {

    override suspend fun login(email: String, password: String): Response<AuthResponse>? {
        return RetrofitClient.apiService.login(email, password)
    }

    override suspend fun forgotPassword(email: String): Response<Message> {
        return RetrofitClient.apiService.fotgotPass(email)
    }

    override suspend fun register(
        email: String,
        name: String,
        password: String,
    ): Response<AuthResponse> {
        return RetrofitClient.apiService.register(email, name, password)
    }

    override suspend fun getCustomer(): Response<Customer>? {
        return RetrofitClient.apiService.getCustomer()
    }
}