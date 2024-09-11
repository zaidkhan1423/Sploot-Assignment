package com.zaid.splootassignment.core.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zaid.splootassignment.presentation.home_screen.HomeScreen
import com.zaid.splootassignment.presentation.home_screen.HomeScreenViewModel

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    onShowSnackBar: suspend (message: String, actionLabel: String?, duration: SnackbarDuration) -> Boolean
) {

    NavHost(navController = navHostController, startDestination = Screen.HomeScreen) {
        composable<Screen.HomeScreen> {

            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            val homeScreenUiState by homeScreenViewModel.homeUiState.collectAsStateWithLifecycle()

            HomeScreen(
                onShowSnackBar = onShowSnackBar,
                uiState = homeScreenUiState,
                onMessageDisplay = { homeScreenViewModel.onMessageDisplayed() }, onEvent = homeScreenViewModel::onEvent
            )
        }

    }
}