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
import com.example.connex.ui.home.AnalyzeScreen
import com.example.connex.ui.home.FriendsScreen
import com.example.connex.ui.home.HomeScreen
import com.example.connex.ui.home.MypageScreen
import com.example.connex.ui.login.view.LoginCompleteScreen
import com.example.connex.ui.login.view.LoginScreen
import com.example.connex.ui.login.view.ProfileInitScreen
import com.example.connex.utils.Constants.FRIEND_SYNC_ROUTE
import com.example.connex.utils.Constants.HOME_GRAPH
import com.example.connex.utils.Constants.HOME_ROUTE
import com.example.connex.utils.Constants.LOGIN_GRAPH
import com.example.connex.utils.Constants.SIGNUP_COMPLETE_ROUTE
import com.example.connex.utils.Constants.SIGNUP_PROFILE_INIT_ROUTE
import com.example.connex.utils.Constants.SIGNUP_START_ROUTE


fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(startDestination = SIGNUP_PROFILE_INIT_ROUTE, route = LOGIN_GRAPH) {
        composable(SIGNUP_START_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = LOGIN_GRAPH
            )
            LoginScreen(
                navController = navController,
                loginViewModel = hiltViewModel(backStackEntry)
            )
        }
        composable(SIGNUP_PROFILE_INIT_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = LOGIN_GRAPH
            )
            ProfileInitScreen(
                navController = navController,
                loginViewModel = hiltViewModel(backStackEntry)
            )
        }
        composable(SIGNUP_COMPLETE_ROUTE) { entry ->
            LoginCompleteScreen(navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.initSettingGraph(navController: NavController) {
    composable(FRIEND_SYNC_ROUTE) { entry ->
        FriendSyncScreen(navController = navController)
    }
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = HOME_ROUTE, route = HOME_GRAPH) {
        composable(Screen.Home.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            HomeScreen()
        }
        composable(Screen.Friends.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            FriendsScreen()
        }
        composable(Screen.Analyze.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            AnalyzeScreen()
        }
        composable(Screen.Mypage.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            MypageScreen()
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