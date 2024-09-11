package com.zaid.splootassignment.presentation.home_screen

import com.zaid.splootassignment.data.model.response.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query

data class HomeScreenUiState(
    val article: List<Article?> = emptyList(),
    val loading: Boolean = true,
    val snackBarMessage: String? = null,
    val searchQuery: String = "",
    val selectedArticle: Article? = null
)
