package com.example.connex

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.util.Consumer
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import com.example.connex.ui.component.BottomBar
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.homeGraph
import com.example.connex.ui.initSettingGraph
import com.example.connex.ui.loginGraph
import com.example.connex.ui.notificationComposable
import com.example.connex.ui.splashComposable
import com.example.connex.utils.Constants

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavhost(
    navBackStackEntry: NavBackStackEntry?,
    appState: ApplicationState,
) {

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(appState.snackbarHostState) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = appState.navController,
//            startDestination = Constants.LOGIN_GRAPH,
                startDestination = Constants.SPLASH_ROUTE,
                modifier = Modifier
                    .fillMaxSize()
                    .customNavigationBarPaading(navBackStackEntry, appState),
            ) {
                Log.d("test", "RootNavhost")

                loginGraph(appState)
                initSettingGraph(appState)
                homeGraph(appState)
                notificationComposable(appState)
                splashComposable(appState)
            }
            BottomBar(appState)
        }
    }

}


private fun Modifier.customNavigationBarPaading(
    navBackStackEntry: NavBackStackEntry?,
    appState: ApplicationState,
): Modifier {
//    if (navBackStackEntry?.destination?.route == Constants.MAP_ROUTE) {
//        return Modifier
//    }
    if (appState.bottomBarState.value) {
        return Modifier.padding(bottom = Constants.BottomNavigationHeight)
//        return Modifier.navigationBarsPadding()

    }
    return Modifier
//    return Modifier.padding(bottom = Constants.BottomNavigationHeight)
}