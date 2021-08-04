package com.example.news_demo.data.api.response

import androidx.room.PrimaryKey

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @PrimaryKey
    val title: String,
    val url: String ?,
    val urlToImage: String?,
    val source: Source
)