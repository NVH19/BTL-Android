package com.example.bookshop.data.repository.author

import com.example.bookshop.data.model.response.author.AuthorFamousList
import com.example.bookshop.data.model.response.author.AuthorInfor
import retrofit2.Response

interface AuthorRepository {
    suspend fun getHotAuthors() : Response<AuthorFamousList>?
    suspend fun getAuthor(authorId: Int): Response<AuthorInfor>?
}