package com.example.bookshop.data.model.response
import com.example.bookshop.data.model.Wishlist
import com.google.gson.annotations.SerializedName

data class WishlistResponse(
    @SerializedName("count") val count : Int,
    @SerializedName("products") val wishlist: List<Wishlist>,
)
