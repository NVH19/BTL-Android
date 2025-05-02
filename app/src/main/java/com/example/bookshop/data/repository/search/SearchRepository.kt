package com.example.bookshop.data.repository.search

import com.example.bookshop.data.model.reponse.product.ProductList
import com.example.bookshop.data.model.reponse.product.ProductNewList
import retrofit2.Response

interface SearchRepository {
    suspend fun getSearchProducts(
        limit: Int,
        page: Int,
        descriptionLength: Int,
        queryString: String,
        filterType: Int,
        priceSortOrder: String,
    ): Response<ProductList>?

    suspend fun getSearchNewProduct(): Response<ProductNewList>?
    suspend fun getSearchHistory(
        queryString: String,
    ): Response<ProductList>
}