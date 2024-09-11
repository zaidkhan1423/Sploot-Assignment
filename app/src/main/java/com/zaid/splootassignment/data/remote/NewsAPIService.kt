package com.zaid.splootassignment.data.remote

import com.zaid.splootassignment.core.utils.Constants.API_KEY
import com.zaid.splootassignment.core.utils.Constants.TOP_HEADLINES
import com.zaid.splootassignment.data.model.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET(TOP_HEADLINES)
    suspend fun getTopNews(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("country") countryCode: String = "us"
    ): Response<NewsResponse>

}