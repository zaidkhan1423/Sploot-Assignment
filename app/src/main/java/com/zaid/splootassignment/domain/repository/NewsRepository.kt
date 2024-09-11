package com.zaid.splootassignment.domain.repository


import com.zaid.splootassignment.data.model.response.NewsResponse
import com.zaid.splootassignment.domain.model.Article
import retrofit2.Response

interface NewsRepository {

    suspend fun getTopNews(): Response<NewsResponse>
    suspend fun saveArticles(articles: List<Article>)
    suspend fun getSavedArticles(): List<Article>
    suspend fun searchNewsByTitle(query: String): List<Article>

}