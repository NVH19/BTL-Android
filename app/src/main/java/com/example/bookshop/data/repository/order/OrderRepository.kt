package com.example.bookshop.data.repository.order


import com.example.bookshop.data.model.response.order.OrderList
import retrofit2.Response


interface OrderRepository {
    suspend fun getOrderHistory(): Response<OrderList>?

}