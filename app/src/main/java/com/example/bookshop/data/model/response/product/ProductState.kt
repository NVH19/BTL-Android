package com.example.bookshop.data.model.response.product

import com.example.bookshop.data.model.Product

data class ProductState(
    val products: List<Product>?,
    val isDefaultState: Boolean
)