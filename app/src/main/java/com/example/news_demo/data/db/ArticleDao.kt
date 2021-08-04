package com.example.news_demo.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles:List<ArticleMapper>)

    @Query("SELECT * FROM articles")
    fun pagingSource(): PagingSource<Int, ArticleMapper>

    @Query("DELETE FROM articles")
    suspend fun deleteAll()

}