package com.example.bookshop.data.model.response

import com.example.bookshop.data.model.Receiver
import com.google.gson.annotations.SerializedName

data class ReceiverResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("receivers") val receivers: List<Receiver>,
)
