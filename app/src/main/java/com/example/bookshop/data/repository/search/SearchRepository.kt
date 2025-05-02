package com.example.bookshop.data.repository.search

import com.example.bookshop.data.model.reponse.product.ProductNewList
import com.example.bookshop.data.model.response.product.ProductList
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
    suspend fun getSearchCategoryProducts(
        limit: Int,
        page: Int,
        descriptionLength: Int,
        queryString: String,
        categroryId: Int,
    ): Response<ProductList>?
    suspend fun getSearchSupplierProducts(
        supplierId: Int,
        limit: Int,
        page: Int,
        descriptionLength: Int,
        queryString: String,
    ): Response<ProductList>?

    suspend fun getSearchAuthorProducts(
        authorId: Int,
        limit: Int,
        page: Int,
        descriptionLength: Int,
        queryString: String,
    ): Response<ProductList>?
}