package com.zaid.splootassignment.data.repository

import com.zaid.splootassignment.data.local.ArticleDao
import com.zaid.splootassignment.data.remote.NewsAPIService
import com.zaid.splootassignment.data.model.response.NewsResponse
import com.zaid.splootassignment.domain.model.Article
import com.zaid.splootassignment.domain.model.toResponse
import com.zaid.splootassignment.domain.repository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsAPIService: NewsAPIService,
    private val articleDao: ArticleDao
) : NewsRepository {

    override suspend fun getTopNews(): Response<NewsResponse> = newsAPIService.getTopNews()

    override suspend fun saveArticles(articles: List<Article>) {
        articleDao.deleteAllArticles()
        articleDao.insertArticles(articles)
    }

    override suspend fun getSavedArticles(): List<Article> {
        return articleDao.getAllArticles()
    }

    override suspend fun searchNewsByTitle(query: String): List<Article> {
        val localNews = articleDao.searchNewsByTitle(query)
        return localNews.map { it }
    }

}