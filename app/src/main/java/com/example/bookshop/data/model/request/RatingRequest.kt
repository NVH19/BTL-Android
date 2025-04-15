package com.example.bookshop.data.model.request

data class RatingRequest(
    val id: Int?=null,
    val comment: String?=null,
    val ratingLevel: Int?=null,
    val bookId: Int?=null,
    val userId: Int?=null,
    val orderId:Int?=null,
)