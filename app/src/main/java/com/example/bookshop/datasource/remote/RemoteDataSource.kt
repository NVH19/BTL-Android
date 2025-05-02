package com.example.bookshop.datasource

import com.example.bookshop.data.api.RetrofitClient
import com.example.bookshop.data.model.CategoryList
import com.example.bookshop.data.model.Customer
import com.example.bookshop.data.model.reponse.Message
import com.example.bookshop.data.model.reponse.RatingResponse
import com.example.bookshop.data.model.reponse.author.AuthorFamousList
import com.example.bookshop.data.model.reponse.author.AuthorInfor
import com.example.bookshop.data.model.reponse.product.BannerList
import com.example.bookshop.data.model.reponse.product.BookInHomeList
import com.example.bookshop.data.model.reponse.product.ProductInfoList
import com.example.bookshop.data.model.reponse.product.ProductList
import com.example.bookshop.data.model.reponse.product.ProductsByAuthor
import com.example.bookshop.data.model.request.RatingRequest
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

    override suspend fun getAllRatingByBook(
        bookId: Int,
        limit: Int,
        page: Int
    ): Response<RatingResponse> {
        return RetrofitClient.apiService.getAllRatingByBook(bookId, limit, page)
    }
    override suspend fun createRatingOrder(ratingRequest: List<RatingRequest>): Response<Message> {
        return RetrofitClient.apiService.createRatingOrder(ratingRequest)
    }
    override suspend fun getProductsBanner(): Response<BannerList>? {
        return RetrofitClient.apiService.getProductBanner()
    }
    override suspend fun getProductInfo(id: Int): Response<ProductInfoList>? {
        return RetrofitClient.apiService.getProductInfo(id)
    }

    override suspend fun getProductsByAuthor(
        id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductsByAuthor>? {
        return RetrofitClient.apiService.getProductsByAuthor(id, limit, page, description_length)
    }
    override suspend fun getProductsByCategory(
        id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductList> {
        return RetrofitClient.apiService.getProductsByCategory(id, limit, page, description_length)
    }

    override suspend fun getProductsBySupplier(
        id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductList>? {
        return RetrofitClient.apiService.getProductsBySupplier(id, limit, page, description_length)
    }

    override suspend fun getAllCategory(): Response<CategoryList>? {
        return RetrofitClient.apiService.getAllCategory()
    }

    override suspend fun getHotCategory(): Response<CategoryList>? {
        return RetrofitClient.apiService.getHotCategory()
    }

    override suspend fun getNewBook(): Response<BookInHomeList>? {
        return RetrofitClient.apiService.getNewBook()
    }

    override suspend fun getHotBook(): Response<BookInHomeList>? {
        return RetrofitClient.apiService.getHotBook()
    }

    override suspend fun getAuthor(authorId: Int): Response<AuthorInfor>? {
        return RetrofitClient.apiService.getAuthor(authorId)
    }

    override suspend fun getHotAuthor(): Response<AuthorFamousList>? {
        return RetrofitClient.apiService.getHotAuthor()
    }
}