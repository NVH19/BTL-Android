package com.example.bookshop.data.model.reponse

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: Error,
)