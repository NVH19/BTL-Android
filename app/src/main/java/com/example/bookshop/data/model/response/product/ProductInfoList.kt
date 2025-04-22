package com.example.bookshop.data.model.response.product

import com.example.bookshop.data.model.Supplier
import com.example.bookshop.data.model.response.author.AuthorProInfo
import com.google.gson.annotations.SerializedName

data class ProductInfoList(
    @SerializedName("product") var product: ProductInfo,
    @SerializedName("supplier") var supplier: Supplier,
    @SerializedName("author") var author: AuthorProInfo,
)