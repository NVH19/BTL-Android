package com.example.bookshop.data.repository.user
import com.example.bookshop.data.model.response.auth.AuthResponse
import com.example.bookshop.data.model.Customer
import com.example.bookshop.data.model.reponse.Message
import com.example.bookshop.datasource.IDataSource
import okhttp3.MultipartBody
import retrofit2.Response

class UserRepositoryImp(private val dataSource: IDataSource) : UserRepository {
    override suspend fun login(email: String, password: String): Response<AuthResponse>? {
        return dataSource.login(email, password)
    }

    override suspend fun forgotPassword(email: String): Response<Message> {
        return dataSource.forgotPassword(email)
    }

    override suspend fun register(
        email: String,
        name: String,
        password: String,
    ): Response<AuthResponse> {
        return dataSource.register(email, name, password)
    }

}