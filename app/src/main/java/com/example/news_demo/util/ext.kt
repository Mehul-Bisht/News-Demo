package com.example.news_demo.util

import com.example.news_demo.data.api.response.Article
import com.example.news_demo.data.db.ArticleMapper

fun Article.toArticleMapper(): ArticleMapper =

    ArticleMapper(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage,
        sourceName = source.name
    )