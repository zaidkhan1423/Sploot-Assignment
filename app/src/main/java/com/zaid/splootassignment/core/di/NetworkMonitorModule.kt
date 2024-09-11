package com.zaid.splootassignment.core.di

import com.zaid.splootassignment.core.presentation.sploot_app.NetworkState
import com.zaid.splootassignment.core.utils.ConnectivityManagerNetworkMonitor
import com.zaid.splootassignment.core.utils.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
interface NetworkMonitorModule {

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor

}