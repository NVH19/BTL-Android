package com.example.bookshop.data.model.response.order

import com.example.bookshop.data.model.Order

data class OrderHistory(
    val header: String?,
    val order: Order?,
):java.io.Serializable