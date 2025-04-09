package com.example.bookshop.data.api

import com.example.bookshop.data.model.reponse.Message
import com.example.bookshop.data.model.response.auth.AuthResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @FormUrlEncoded
    @POST("customers/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("customers/forgotPass")
    suspend fun fotgotPass(
        @Field("email") email: String,
    ): Response<Message>

    @FormUrlEncoded
    @POST("customers")
    suspend fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
    ): Response<AuthResponse>
}