package com.zaid.splootassignment.presentation.home_screen

import com.zaid.splootassignment.data.model.response.Article

sealed interface HomeScreenUiEvent {

    data class OnSearchTextChange(val query: String) : HomeScreenUiEvent
    data object OnSearchNews: HomeScreenUiEvent
    data class OnSelectArticle(val article: Article?): HomeScreenUiEvent

}