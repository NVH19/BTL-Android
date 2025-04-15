package com.example.bookshop.data.model.reponse.product

import com.google.gson.annotations.SerializedName

data class BookInHomeList(
    @SerializedName("count") var count: Int?,
    @SerializedName("products") var products: List<BookInHome>,
)