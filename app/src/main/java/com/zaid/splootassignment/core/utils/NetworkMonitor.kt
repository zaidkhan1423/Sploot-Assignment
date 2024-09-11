package com.zaid.splootassignment.core.utils

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnlineFlow: Flow<Boolean>
    val isOnline: Boolean
}