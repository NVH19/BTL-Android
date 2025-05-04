package com.example.bookshop.data.model.response

import com.example.bookshop.data.model.Rating
import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("totalRating" ) var totalRating : Int?,
    @SerializedName("ratingDtos"  ) var ratingDtos  : List<Rating>,
)