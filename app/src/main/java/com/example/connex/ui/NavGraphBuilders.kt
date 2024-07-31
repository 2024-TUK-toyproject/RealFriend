package com.example.connex.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.connex.ui.friendinit.view.FriendSyncScreen
import com.example.connex.ui.login.view.LoginCompleteScreen
import com.example.connex.ui.login.view.LoginScreen
import com.example.connex.ui.login.view.ProfileInitScreen
import com.example.connex.utils.Constants.FRIEND_SYNC_ROUTE
import com.example.connex.utils.Constants.INIT_SETTING_GRAPH
import com.example.connex.utils.Constants.LOGIN_GRAPH
import com.example.connex.utils.Constants.SIGNUP_COMPLETE_ROUTE
import com.example.connex.utils.Constants.SIGNUP_PROFILE_INIT_ROUTE
import com.example.connex.utils.Constants.SIGNUP_START_ROUTE


fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(startDestination = SIGNUP_START_ROUTE, route = LOGIN_GRAPH) {
        composable(SIGNUP_START_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = LOGIN_GRAPH
            )
            LoginScreen(navController = navController, loginViewModel = hiltViewModel(backStackEntry))
        }
        composable(SIGNUP_PROFILE_INIT_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = LOGIN_GRAPH
            )
            ProfileInitScreen(navController = navController, loginViewModel = hiltViewModel(backStackEntry))
        }
        composable(SIGNUP_COMPLETE_ROUTE) { entry ->
            LoginCompleteScreen(navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.initSettingGraph(navController: NavController) {
    navigation(startDestination = FRIEND_SYNC_ROUTE, route = INIT_SETTING_GRAPH) {
        composable(FRIEND_SYNC_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = INIT_SETTING_GRAPH
            )
            FriendSyncScreen(navController = navController, friendSyncViewModel = hiltViewModel(backStackEntry))
        }

    }
}


@Composable
private fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}