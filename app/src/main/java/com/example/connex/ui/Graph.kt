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
import com.example.connex.ui.friendinit.view.FriendSyncCompleteScreen
import com.example.connex.ui.friendinit.view.FriendSyncScreen
import com.example.connex.ui.home.view.AlbumScreen
import com.example.connex.ui.home.view.FriendsRemoveScreen
import com.example.connex.ui.home.view.FriendsScreen
import com.example.connex.ui.home.view.HomeScreen
import com.example.connex.ui.home.view.MypageScreen
import com.example.connex.ui.login.view.LoginCompleteScreen
import com.example.connex.ui.login.view.LoginScreen
import com.example.connex.ui.login.view.ProfileInitScreen
import com.example.connex.utils.Constants.FRIEND_REMOVE_ROUTE
import com.example.connex.utils.Constants.FRIEND_SYNC_COMPLETE_ROUTE
import com.example.connex.utils.Constants.FRIEND_SYNC_ROUTE
import com.example.connex.utils.Constants.HOME_GRAPH
import com.example.connex.utils.Constants.HOME_ROUTE
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
        composable("${SIGNUP_COMPLETE_ROUTE}/{userId}/{name}") { entry ->
            LoginCompleteScreen(
                navController = navController,
                userId = entry.arguments?.getString("userId") ?: "111_111",
                name = entry.arguments?.getString("name") ?: "새싹"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.initSettingGraph(navController: NavController) {
    navigation(startDestination = FRIEND_SYNC_ROUTE, route = INIT_SETTING_GRAPH) {
        composable("${FRIEND_SYNC_ROUTE}/{userId}/{name}") { entry ->
            FriendSyncScreen(
                navController = navController,
                name = entry.arguments?.getString("name") ?: "새싹",
                userId = entry.arguments?.getString("userId")?.toLong() ?: 111_111L,
            )
        }
        composable("${FRIEND_SYNC_COMPLETE_ROUTE}/{userId}/{name}") { entry ->
            FriendSyncCompleteScreen(
                navController = navController,
                name = entry.arguments?.getString("name") ?: "새싹",
                userId = entry.arguments?.getString("userId")?.toLong() ?: 111_111L,
            )
        }
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
            HomeScreen(navController = navController)
        }
        composable(Screen.Friends.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            FriendsScreen(friendsViewModel = hiltViewModel(backStackEntry), navController = navController)
        }
        composable(Screen.Album.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            AlbumScreen()
        }
        composable(Screen.Mypage.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            MypageScreen()
        }

        composable(Screen.Mypage.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            MypageScreen()
        }

        composable(FRIEND_REMOVE_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = navController,
                graph = HOME_GRAPH
            )
            FriendsRemoveScreen(friendsViewModel = hiltViewModel(backStackEntry), navController = navController)
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