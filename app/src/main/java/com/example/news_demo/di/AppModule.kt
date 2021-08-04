package com.example.news_demo.di

import android.content.Context
import androidx.room.Room
import com.example.news_demo.data.NewsRepository
import com.example.news_demo.data.api.NewsService
import com.example.news_demo.data.db.ArticleDB
import com.example.news_demo.data.db.ArticleDao
import com.example.news_demo.data.db.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): ArticleDB =

        Room.databaseBuilder(
        app,
        ArticleDB::class.java,
        "article_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideArticleDao(db: ArticleDB): ArticleDao =
        db.getArticleDao()

    @Singleton
    @Provides
    fun provideRemoteKeyDao(db: ArticleDB): RemoteKeysDao =
        db.getRemoteKeyDao()

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =

        OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(NewsService.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Singleton
    @Provides
    fun provideNewsApiRepository(
        service: NewsService,
        database: ArticleDB,
    ): NewsRepository =

        NewsRepository(
            service,
            database
        )
}