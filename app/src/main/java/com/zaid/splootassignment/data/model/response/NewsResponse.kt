package com.zaid.splootassignment.data.model.response

data class NewsResponse(
    val articles: List<Article>,
    val status: String?,
    val totalResults: Int?
)