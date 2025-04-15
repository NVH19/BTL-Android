package com.example.bookshop.data.model.reponse.product

import com.google.gson.annotations.SerializedName

data class BannerList(
    @SerializedName("count") var count: Int? = null,
    @SerializedName("products") var products: List<Banner>,
)