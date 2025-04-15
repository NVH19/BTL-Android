package com.example.bookshop.data.repository.category

import com.example.bookshop.data.model.CategoryList
import retrofit2.Response


interface CategoryRepository {
    suspend fun getAllCategory(): Response<CategoryList>?
    suspend fun getHotCategory():Response<CategoryList>?
}