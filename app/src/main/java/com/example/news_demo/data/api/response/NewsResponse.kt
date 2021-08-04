package com.example.news_demo.data.api.response

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)