package com.zaid.splootassignment.di

import com.zaid.splootassignment.data.remote.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NewsAPIService =
        retrofit.create(NewsAPIService::class.java)

}