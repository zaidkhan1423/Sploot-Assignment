package com.zaid.splootassignment.di

import com.zaid.splootassignment.data.repository.NewsRepositoryImpl
import com.zaid.splootassignment.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsHomeRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

}