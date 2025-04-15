package com.example.bookshop.data.repository.product

import com.example.bookshop.data.model.reponse.Message
import com.example.bookshop.data.model.reponse.RatingResponse
import com.example.bookshop.data.model.reponse.product.BannerList
import com.example.bookshop.data.model.reponse.product.BookInHomeList
import com.example.bookshop.data.model.reponse.product.ProductInfoList
import com.example.bookshop.data.model.reponse.product.ProductList
import com.example.bookshop.data.model.reponse.product.ProductsByAuthor
import com.example.bookshop.data.model.request.RatingRequest
import com.example.bookshop.datasource.IDataSource
import retrofit2.Response

class ProductRepositoryImp(private val dataSource: IDataSource) : ProductRepository {
    override suspend fun createRatingOrder(ratingRequest: List<RatingRequest>): Response<Message> {
        return dataSource.createRatingOrder(ratingRequest)
    }
    override suspend fun getAllRatingByBook(
        bookId: Int,
        limit: Int,
        page: Int
    ): Response<RatingResponse> {
        return dataSource.getAllRatingByBook(bookId, limit, page)
    }

    override suspend fun getProductsBanner(): Response<BannerList>? {
        return dataSource.getProductsBanner()
    }

    override suspend fun getProductInfo(id: Int): Response<ProductInfoList>? {
        return dataSource.getProductInfo(id)
    }

    override suspend fun getProductsByAuthor(
        author_id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductsByAuthor>? {
        return dataSource.getProductsByAuthor(author_id, limit, page, description_length)
    }

    override suspend fun getProductsByCategory(
        author_id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductList>? {
        return dataSource.getProductsByCategory(author_id, limit, page, description_length)
    }

    override suspend fun getProductsBySupplier(
        id: Int,
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<ProductList>? {
        return dataSource.getProductsBySupplier(id, limit, page, description_length)
    }

    override suspend fun getNewBook(): Response<BookInHomeList>? {
        return dataSource.getNewBook()
    }

    override suspend fun getHotBook(): Response<BookInHomeList>? {
        return dataSource.getHotBook()
    }
}