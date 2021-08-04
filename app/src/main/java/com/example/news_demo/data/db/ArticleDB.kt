package com.example.news_demo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleMapper::class, RemoteKey::class],
    version = 1
)
abstract class ArticleDB: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao
    abstract fun getRemoteKeyDao(): RemoteKeysDao

}