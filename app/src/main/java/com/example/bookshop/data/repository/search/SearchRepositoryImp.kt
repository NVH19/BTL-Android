package com.example.bookshop.data.repository.search

import com.example.bookshop.data.model.response.product.ProductNewList
import com.example.bookshop.data.model.response.product.ProductList

import com.example.bookshop.datasource.IDataSource
import retrofit2.Response

class SearchRepositoryImp(private val dataSource: IDataSource) : SearchRepository {
    override suspend fun getSearchProducts(
        limit: Int,
        page: Int,
        descriptionLength: Int,
        queryString: String,
        filterType: Int,
        priceSortOrder: String,
    ): Response<ProductList>? {
        return dataSource.getSearchProducts(
            limit,
            page,
            descriptionLength,
            queryString,
            filterType,
            priceSortOrder,
        )
    }

    override suspend fun getSearchNewProduct(): Response<ProductNewList>? {
        return dataSource.getSearchNewProduct()
    }
    override suspend fun getSearchCategoryProducts(
        limit: Int,
        page: Int,
        descriptionLength: Int,
        queryString: String,
        categroryId: Int,
    ): Response<ProductList>? {
        return dataSource.getSearchCategoryProducts(
            limit,
            page,
            descriptionLength,
            queryString,
            categroryId,
        )
    }
    override suspend fun getSearchSupplierProducts(
        supplierId: Int,
        limit: Int,
        page: Int,
        descriptionLength: Int,
        queryString: String,
    ): Response<ProductList>? {
        return dataSource.getSearchSupplierProducts(
            supplierId,
            limit,
            page,
            descriptionLength,
            queryString
        )
    }

    override suspend fun getSearchAuthorProducts(
        authorId: Int,
        limit: Int,
        page: Int,
        descriptionLength: Int,
        queryString: String,
    ): Response<ProductList>? {
        return dataSource.getSearchAuthorProducts(
            authorId,
            limit,
            page,
            descriptionLength,
            queryString,
        )
    }

    override suspend fun getProductSuggestions(queryString: String): Response<ProductList> {
        return dataSource.getProductSuggestions(queryString)
    }
}