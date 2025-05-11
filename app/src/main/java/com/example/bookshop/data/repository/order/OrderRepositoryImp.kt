package com.example.bookshop.data.repository.order

import com.example.bookshop.data.model.response.Message
import com.example.bookshop.data.model.response.order.OrderList
import com.example.bookshop.datasource.IDataSource
import retrofit2.Response

class OrderRepositoryImp(private val dataSource: IDataSource) : OrderRepository {
    override suspend fun getOrderHistory(): Response<OrderList>? {
        return dataSource.getOrderHistory()
    }

    override suspend fun createOrder(
        cartId: String,
        shippingId: Int,
        receiverId: Int,
        paymentId: Int,
    ): Response<Message> {
        return dataSource.createOrder(
            cartId,
            shippingId,
            receiverId,
            paymentId
        )
    }
}