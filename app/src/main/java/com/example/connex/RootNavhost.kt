package com.example.connex

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.connex.ui.component.BottomBar
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.homeGraph
import com.example.connex.ui.initSettingGraph
import com.example.connex.ui.loginGraph
import com.example.connex.utils.Constants
import com.example.connex.utils.Constants.INIT_SETTING_GRAPH
import com.example.connex.utils.Constants.LOGIN_GRAPH

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavhost(
    navBackStackEntry: NavBackStackEntry?,
    appState: ApplicationState,
) {
    Box(
        modifier = customNavigationBarPaading(navBackStackEntry, appState)
            .fillMaxSize()
    ) {
        NavHost(
            navController = appState.navController,
            startDestination = Constants.FRIEND_SYNC_ROUTE,
            modifier = Modifier.fillMaxSize()
        ) {
            loginGraph(appState.navController)
            initSettingGraph(appState.navController)
            homeGraph(appState.navController)
        }
        BottomBar(appState = appState)
    }

}


private fun customNavigationBarPaading(
    navBackStackEntry: NavBackStackEntry?,
    appState: ApplicationState,
): Modifier {
//    if (navBackStackEntry?.destination?.route == Constants.MAP_ROUTE) {
//        return Modifier
//    }
    if (appState.bottomBarState.value) {
        return Modifier
    }
    return Modifier.navigationBarsPadding()
}