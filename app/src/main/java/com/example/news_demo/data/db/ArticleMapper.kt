package com.example.news_demo.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "articles")
data class ArticleMapper(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @PrimaryKey
    val title: String,
    val url: String ?,
    val urlToImage: String?,
    val sourceName: String
): Parcelable