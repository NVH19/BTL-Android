package com.example.bookshop.data.model.response.author

import com.example.bookshop.data.model.Author
import com.google.gson.annotations.SerializedName

data class AuthorInfor(
    @SerializedName("result" ) var author : Author,
)