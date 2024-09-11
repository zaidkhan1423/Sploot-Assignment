package com.zaid.splootassignment.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object HomeScreen : Screen()

}