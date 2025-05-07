package com.example.bookshop.datasource

import com.example.bookshop.data.model.Cart
import com.example.bookshop.data.model.CartItem
import com.example.bookshop.data.model.*
import com.example.bookshop.data.model.CategoryList
import com.example.bookshop.data.model.Customer
import com.example.bookshop.data.model.reponse.product.ProductNewList
import com.example.bookshop.data.model.response.*
import com.example.bookshop.data.model.response.RatingResponse
import com.example.bookshop.data.model.response.author.AuthorFamousList
import com.example.bookshop.data.model.response.author.AuthorInfor
import com.example.bookshop.data.model.response.product.*
import com.example.bookshop.data.model.request.*
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


    suspend fun addItemToWishList(productId: Int): Response<Message>?
    suspend fun removeItemInWishList(productId: Int): Response<Message>
    suspend fun getWishList(
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<WishlistResponse>?

    suspend fun getAllCategory(): Response<CategoryList>?
    suspend fun getHotCategory(): Response<CategoryList>?
    suspend fun getNewBook(): Response<BookInHomeList>?
    suspend fun getHotBook(): Response<BookInHomeList>?

    suspend fun getAuthor(authorId: Int): Response<AuthorInfor>?
    suspend fun getHotAuthor(): Response<AuthorFamousList>?

    suspend fun addCartItem(productId: Int): Response<List<CartItem>>?
    suspend fun getAllCart(): Response<Cart>?
    suspend fun addAllItemToCart(): Response<Message>
    suspend fun deleteAllItemCart(): Response<Message>
    suspend fun changeProductQuantityInCart(itemId: Int, quantity: Int): Response<Message>?
    suspend fun removeItemInCart(itemId: Int): Response<Message>?

    suspend fun getSearchProducts(
        limit: Int,
        page: Int,
        description_length: Int,
        query_string: String,
        filter_type: Int,
        price_sort_order: String,
    ): Response<ProductList>?
    suspend fun getSearchNewProduct(): Response<ProductNewList>?
    suspend fun getSearchCategoryProducts(
        limit: Int,
        page: Int,
        description_length: Int,
        query_string: String,
        categoryId: Int,
    ): Response<ProductList>?
}