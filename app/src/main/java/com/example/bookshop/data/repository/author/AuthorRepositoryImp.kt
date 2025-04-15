package com.example.bookshop.data.repository.author

import com.example.bookshop.data.model.reponse.author.AuthorFamousList
import com.example.bookshop.data.model.reponse.author.AuthorInfor
import com.example.bookshop.datasource.IDataSource
import retrofit2.Response

class AuthorRepositoryImp(private val iDataSource: IDataSource) : AuthorRepository {
    override suspend fun getHotAuthors(): Response<AuthorFamousList>? {
        return iDataSource.getHotAuthor()
    }

    override suspend fun getAuthor(authorId: Int): Response<AuthorInfor>? {
        return iDataSource.getAuthor(authorId)
    }
}