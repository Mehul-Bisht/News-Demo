package com.example.news_demo.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news_demo.data.api.NewsService
import com.example.news_demo.data.db.ArticleDB
import com.example.news_demo.data.db.ArticleMapper
import com.example.news_demo.data.db.ArticleRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val service: NewsService,
    private val database: ArticleDB,
) {

    @ExperimentalPagingApi
    fun getHeadLines(): Flow<PagingData<ArticleMapper>> {

        val pagingSourceFactory = { database.getArticleDao().pagingSource() }

        return Pager(
            config = PagingConfig(
                pageSize = 8,
                maxSize = 24,
                enablePlaceholders = false
            ),
            remoteMediator = ArticleRemoteMediator(
                database,
                service
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}