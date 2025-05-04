package com.example.bookshop.data.model.reponse.product

import com.example.bookshop.data.model.Product

data class ProductState(
    val products: List<Product>?,
    val isDefaultState: Boolean
)