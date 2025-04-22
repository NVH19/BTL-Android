package com.example.bookshop.data.model.response.product

import com.example.bookshop.data.model.Product
import com.google.gson.annotations.SerializedName

data class ProductsByAuthor(
    @SerializedName("count") var count: Int,
    @SerializedName("rows") var products: List<Product>,
)