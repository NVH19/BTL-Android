package com.example.bookshop.data.model.response.order

import com.example.bookshop.data.model.Order
import com.google.gson.annotations.SerializedName

data class OrderList(
    @SerializedName("count") var count: Int,
    @SerializedName("orders") var orders: List<Order>,
)