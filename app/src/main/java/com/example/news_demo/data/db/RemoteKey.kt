package com.example.news_demo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKeys")
data class RemoteKey(

    @PrimaryKey
    val newsTitle: String,
    val prevKey: Int?=null,
    val nextKey: Int?=null
)