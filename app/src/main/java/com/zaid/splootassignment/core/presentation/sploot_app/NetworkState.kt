package com.zaid.splootassignment.core.presentation.sploot_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.zaid.splootassignment.core.utils.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberNetworkState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor
): NetworkState {
    return remember(
        coroutineScope,
        networkMonitor
    ) {
        NetworkState(
            networkMonitor,
            coroutineScope
        )
    }
}

class NetworkState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope
) {
    val isOffline = networkMonitor.isOnlineFlow
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )
}