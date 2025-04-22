package com.example.bookshop.datasource.remote

import com.example.bookshop.data.api.RetrofitClient
import com.example.bookshop.data.model.*
import com.example.bookshop.data.model.response.*
import com.example.bookshop.data.model.response.author.AuthorFamousList
import com.example.bookshop.data.model.response.author.AuthorInfor
import com.example.bookshop.data.model.response.product.*
import com.example.bookshop.data.model.request.*
import com.example.bookshop.data.model.response.auth.AuthResponse
import com.example.bookshop.datasource.IDataSource
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

    override suspend fun addItemToWishList(productId: Int): Response<Message>? {
        return RetrofitClient.apiService.addItemToWishList(productId)
    }

    override suspend fun removeItemInWishList(productId: Int): Response<Message> {
        return RetrofitClient.apiService.removeItemInWishList(productId)
    }

    override suspend fun getWishList(
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<WishlistResponse>? {
        return RetrofitClient.apiService.getWishList(limit, page, description_length)
    }

    override suspend fun getAllCart(): Response<Cart>? {
        return RetrofitClient.apiService.getAllCart()
    }

    override suspend fun addCartItem(productId: Int): Response<List<CartItem>>? {
        return RetrofitClient.apiService.addProduct2Cart(productId)
    }

    override suspend fun addAllItemToCart(): Response<Message> {
        return RetrofitClient.apiService.addAllItem2Cart()
    }

    override suspend fun deleteAllItemCart(): Response<Message> {
        return RetrofitClient.apiService.deleteAllItemCart()
    }

    override suspend fun changeProductQuantityInCart(
        itemId: Int,
        quantity: Int,
    ): Response<Message>? {
        return RetrofitClient.apiService.changeProductQuantityInCart(itemId, quantity)
    }

    override suspend fun removeItemInCart(itemId: Int): Response<Message>? {
        return RetrofitClient.apiService.removeItemInCart(itemId)
    }


}