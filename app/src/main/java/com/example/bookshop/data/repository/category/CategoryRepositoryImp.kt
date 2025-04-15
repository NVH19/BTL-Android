package com.example.bookshop.data.repository.category

import com.example.bookshop.data.model.CategoryList
import com.example.bookshop.datasource.IDataSource
import retrofit2.Response

class CategoryRepositoryImp(private val dataSource: IDataSource) : CategoryRepository {
    override suspend fun getAllCategory(): Response<CategoryList>? {
        return dataSource.getAllCategory()
    }

    override suspend fun getHotCategory(): Response<CategoryList>? {
        return dataSource.getHotCategory()
    }
}