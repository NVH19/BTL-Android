package com.example.bookshop.datasource

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
import com.example.bookshop.data.model.reponse.product.ProductNewList
import com.example.bookshop.data.model.reponse.product.ProductsByAuthor
import com.example.bookshop.data.model.request.RatingRequest
import com.example.bookshop.data.model.response.auth.AuthResponse
import retrofit2.Response

interface IDataSource {
    suspend fun login(email: String, password: String): Response<AuthResponse>?
    suspend fun forgotPassword(email: String): Response<Message>
    suspend fun register(email: String, name: String, password: String): Response<AuthResponse>
    suspend fun getCustomer(): Response<Customer>?
    suspend fun createRatingOrder(ratingRequest:List<RatingRequest>):Response<Message>
    suspend fun getAllRatingByBook(
        bookId: Int,
        limit:Int,
        page: Int,
    ): Response<RatingResponse>
    suspend fun getProductsBanner(): Response<BannerList>?
    suspend fun getProductInfo(id: Int): Response<ProductInfoList>?
    suspend fun getProductsByAuthor(
        id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductsByAuthor>?

    suspend fun getProductsByCategory(
        id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductList>?

    suspend fun getProductsBySupplier(
        id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductList>?

    suspend fun getSearchNewProduct(): Response<ProductNewList>?
    suspend fun getSearchProducts(
        limit: Int,
        page: Int,
        description_length: Int,
        query_string: String,
        filter_type: Int,
        price_sort_order: String,
    ): Response<ProductList>?

    suspend fun getAllCategory(): Response<CategoryList>?
    suspend fun getHotCategory(): Response<CategoryList>?
    suspend fun getNewBook(): Response<BookInHomeList>?
    suspend fun getHotBook(): Response<BookInHomeList>?

    suspend fun getAuthor(authorId: Int): Response<AuthorInfor>?
    suspend fun getHotAuthor(): Response<AuthorFamousList>?

}