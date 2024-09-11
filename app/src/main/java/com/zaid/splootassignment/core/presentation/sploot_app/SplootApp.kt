package com.zaid.splootassignment.core.presentation.sploot_app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zaid.splootassignment.R
import com.zaid.splootassignment.core.navigation.AppNavHost
import com.zaid.splootassignment.core.utils.ConnectivityManagerNetworkMonitor
import com.zaid.splootassignment.core.utils.NetworkMonitor
import kotlinx.coroutines.delay

@Composable
fun SplootApp(
    navHostController: NavHostController = rememberNavController(),
    networkMonitor: NetworkMonitor,
    networkState: NetworkState = rememberNetworkState(networkMonitor = networkMonitor)
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val isOffline by networkState.isOffline.collectAsStateWithLifecycle()
    var showBackOnlineMessage by remember { mutableStateOf(false) }
    var showNoInternetMessage by remember { mutableStateOf(false) }

    LaunchedEffect(isOffline) {
        if (isOffline) {
            showNoInternetMessage = true
        } else {
            showBackOnlineMessage = true
        }
    }

    LaunchedEffect(!isOffline) {
        if (showBackOnlineMessage) {
            delay(5000)
            showBackOnlineMessage = false
        }
    }

    LaunchedEffect(isOffline) {
        if (showNoInternetMessage && isOffline) {
            delay(5000)
            showNoInternetMessage = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AppNavHost(
                navHostController = navHostController,
                onShowSnackBar = { message, action, duration ->
                    snackBarHostState.showSnackbar(
                        message = message,
                        actionLabel = action,
                        duration = duration,
                        withDismissAction = duration == SnackbarDuration.Indefinite
                    ) == SnackbarResult.ActionPerformed
                },
            )

            AnimatedVisibility(
                visible = showBackOnlineMessage && !isOffline,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it }),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFF4CAF50))
                        .height(35.dp)
                        .align(Alignment.TopCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_network_avilable),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Back Online!",
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = showNoInternetMessage && isOffline,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it }),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color(0xFFF53C3C))
                        .height(35.dp)
                        .align(Alignment.TopCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_network_unavilable),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "No internet connection!",
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun AppPreview() {
    SplootApp(networkMonitor = ConnectivityManagerNetworkMonitor(context = LocalContext.current))
}
