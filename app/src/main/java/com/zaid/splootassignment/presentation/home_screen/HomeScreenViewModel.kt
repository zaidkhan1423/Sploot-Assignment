package com.zaid.splootassignment.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaid.splootassignment.core.utils.NetworkMonitor
import com.zaid.splootassignment.domain.model.toLocalList
import com.zaid.splootassignment.domain.model.toResponseList
import com.zaid.splootassignment.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeScreenUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        getTopNews()
    }

    fun onEvent(event: HomeScreenUiEvent){
        when(event){
            is HomeScreenUiEvent.OnSearchTextChange -> {
                _homeUiState.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
            }

            HomeScreenUiEvent.OnSearchNews -> searchNews()
            is HomeScreenUiEvent.OnSelectArticle -> {
                _homeUiState.update {
                    it.copy(
                        selectedArticle = event.article
                    )
                }
            }
        }
    }


    fun onMessageDisplayed() {
        _homeUiState.update {
            it.copy(
                snackBarMessage = null
            )
        }
    }

    private fun getTopNews() {
        viewModelScope.launch(Dispatchers.IO) {
            _homeUiState.update { uiState ->
                uiState.copy(loading = true)
            }
            if (networkMonitor.isOnline) {
                try {
                    val result = newsRepository.getTopNews()
                    if (result.isSuccessful && result.body() != null) {
                        val data = result.body()
                        _homeUiState.update { uiState ->
                            uiState.copy(
                                loading = false,
                                article = data?.articles ?: emptyList(),
                                snackBarMessage = "Data Fetch Successfully"
                            )
                        }
                        newsRepository.saveArticles(data?.articles.toLocalList())
                    } else {
                        val errorMessage = result.message() ?: "Unknown error"
                        _homeUiState.update { uiState ->
                            uiState.copy(
                                loading = false,
                                snackBarMessage = errorMessage
                            )
                        }
                    }
                } catch (e: Exception) {
                    _homeUiState.update { uiState ->
                        uiState.copy(
                            loading = false,
                            snackBarMessage = "An unexpected error occurred"
                        )
                    }
                }
            } else {
                _homeUiState.update { uiState ->
                    uiState.copy(
                        loading = false,
                        snackBarMessage = "You are viewing saved news",
                        article = newsRepository.getSavedArticles().toResponseList()
                    )
                }
            }

        }
    }


    private fun searchNews() {
        viewModelScope.launch {
            _homeUiState.update { it.copy(loading = true) }
            try {
                val searchResults = newsRepository.searchNewsByTitle(homeUiState.value.searchQuery)
                _homeUiState.update {
                    it.copy(
                        loading = false,
                        article = searchResults.toResponseList(),
                        snackBarMessage = if (searchResults.isEmpty()) "No results found" else null
                    )
                }
            } catch (e: Exception) {
                _homeUiState.update {
                    it.copy(
                        loading = false,
                        snackBarMessage = "An error occurred during search"
                    )
                }
            }
        }
    }


}