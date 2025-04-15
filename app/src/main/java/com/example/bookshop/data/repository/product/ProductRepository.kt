package com.example.bookshop.data.repository.product

import com.example.bookshop.data.model.reponse.Message
import com.example.bookshop.data.model.reponse.RatingResponse
import com.example.bookshop.data.model.reponse.product.BannerList
import com.example.bookshop.data.model.reponse.product.BookInHomeList
import com.example.bookshop.data.model.reponse.product.ProductInfoList
import com.example.bookshop.data.model.reponse.product.ProductList
import com.example.bookshop.data.model.reponse.product.ProductsByAuthor
import com.example.bookshop.data.model.request.RatingRequest
import retrofit2.Response

interface ProductRepository {
    suspend fun createRatingOrder(ratingRequest: List<RatingRequest>): Response<Message>
    suspend fun getAllRatingByBook(bookId: Int, limit: Int, page: Int): Response<RatingResponse>
    suspend fun getProductsBanner(): Response<BannerList>?
    suspend fun getProductInfo(id: Int): Response<ProductInfoList>?
    suspend fun getProductsByAuthor(
        author_id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductsByAuthor>?

    suspend fun getProductsByCategory(
        author_id: Int,
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

    suspend fun getNewBook(): Response<BookInHomeList>?
    suspend fun getHotBook(): Response<BookInHomeList>?
}