package com.zaid.splootassignment.di

import com.zaid.splootassignment.data.local.ArticleDao
import com.zaid.splootassignment.data.local.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideArticleDao(database: NewsDatabase): ArticleDao {
        return database.articleDao()
    }

}