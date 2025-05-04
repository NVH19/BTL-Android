package com.example.bookshop.data.repository.wishlist

import com.example.bookshop.data.model.response.Message
import com.example.bookshop.data.model.response.WishlistResponse
import retrofit2.Response

interface WishListRepository {
    suspend fun addItemToWishList(productId: Int): Response<Message>?
    suspend fun removeItemInWishList(productId: Int): Response<Message>?

    suspend fun getWishList(
        limit: Int,
        page: Int,
        description_length: Int,
    ): Response<WishlistResponse>?
}