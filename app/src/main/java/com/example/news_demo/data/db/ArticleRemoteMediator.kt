package com.example.news_demo.data.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.news_demo.data.api.NewsService
import com.example.news_demo.util.Constants
import com.example.news_demo.util.toArticleMapper
import java.lang.Exception

private var STARTING_PAGE = 1

@ExperimentalPagingApi
class ArticleRemoteMediator(
    private val db: ArticleDB ,
    private val api: NewsService
):RemoteMediator<Int, ArticleMapper>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleMapper>
    ): MediatorResult {
        val loadKey = when (loadType) {

            LoadType.REFRESH -> {
                null
            }
            LoadType.PREPEND ->{
                val remoteKey = getRemoteKeyForFirstTime(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastTime(state)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }

        try {
            val response = api.getHeadlines(
                Constants.NEWS_COUNTRY_CODE,
                Constants.NEWS_API_KEY,
                loadKey ?: STARTING_PAGE
            )

            val endOfPagination = response.articles.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH){
                    db.getRemoteKeyDao().clearRemoteKeys()
                    db.getArticleDao().deleteAll()
                }
                val page = loadKey ?: STARTING_PAGE
                val prevKey = if (loadKey == STARTING_PAGE) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1

                val keys = response.articles.map {
                    RemoteKey(newsTitle = it.title,prevKey=prevKey, nextKey=nextKey)
                }
                db.getRemoteKeyDao().insertAll(keys)
                db.getArticleDao().insertAll(
                    response.articles.map { it.toArticleMapper() }
                )
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        }catch (e:Exception){
            e.printStackTrace()
            return MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArticleMapper>): RemoteKey? {
        return state.anchorPosition?.let { posotion ->
            state.closestItemToPosition(posotion)?.title?.let {
                db.getRemoteKeyDao().remoteKeysRepoId(title = it)
            }
        }
    }

    private suspend fun getRemoteKeyForLastTime(state: PagingState<Int, ArticleMapper>): RemoteKey?{
        return state.pages.lastOrNull(){it.data.isNotEmpty()} ?.data?.lastOrNull()
            ?.let {
                db.getRemoteKeyDao().remoteKeysRepoId(title = it.title)
            }
    }

    private suspend fun getRemoteKeyForFirstTime(state: PagingState<Int, ArticleMapper>): RemoteKey?{
        return state.pages.firstOrNull(){it.data.isNotEmpty()} ?.data?.firstOrNull()
            ?.let {
                db.getRemoteKeyDao().remoteKeysRepoId(title = it.title)
            }
    }
}