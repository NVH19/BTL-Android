package com.example.bookshop.data.model.response

import com.example.bookshop.data.model.Product

data class HistorySearch(
    val historyLocal: String?,
    val historySuggest: Product?,
)