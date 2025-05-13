package com.example.bookshop.data.repository.order


import com.example.bookshop.data.model.OrderDetail
import com.example.bookshop.data.model.response.Message
import com.example.bookshop.data.model.response.order.OrderList
import retrofit2.Response


interface OrderRepository {
    suspend fun getOrderHistory(): Response<OrderList>?
    suspend fun createOrder(
        cartId: String,
        shippingId: Int,
        receiverId: Int,
        paymentId: Int,
    ): Response<Message>
    suspend fun getOrderDetail(orderId: Int): Response<OrderDetail>?
    suspend fun updateOrderStatus(orderId: Int, orderStatusId: Int): Response<Message>
}