package com.example.bookshop.data.model.reponse.author

import com.google.gson.annotations.SerializedName

data class AuthorProInfo(
    @SerializedName("author_id") var authorId: Int,
    @SerializedName("author_name") var authorName: String,
)