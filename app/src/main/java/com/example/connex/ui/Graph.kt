package com.example.connex.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.connex.ui.album.view.AddPictureScreen
import com.example.connex.ui.album.view.PhotosScreen
import com.example.connex.ui.album_create.view.CreatingAlbumCompleteScreen
import com.example.connex.ui.album_create.view.CreatingAlbumScreen
import com.example.connex.ui.album_setting.ui.AddMemberToAlbumScreen
import com.example.connex.ui.album_setting.ui.AlbumInfoScreen
import com.example.connex.ui.album_setting.ui.MembersOfAlbumScreen
import com.example.connex.ui.album_setting.ui.AlbumSettingScreen
import com.example.connex.ui.album_setting.ui.MemberGrantedSettingScreen
import com.example.connex.ui.album_setting.ui.ThumbnailSettingScreen
import com.example.connex.ui.albumphoto.view.PhotoCommentScreen
import com.example.connex.ui.albumphoto.view.PhotoOfAlbumScreen
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.contact_recommend.view.RecommendContactLoadingScreen
import com.example.connex.ui.contact_recommend.view.RecommendContactResultScreen
import com.example.connex.ui.friendinit.view.FriendSyncCompleteScreen
import com.example.connex.ui.friendinit.view.FriendSyncScreen
import com.example.connex.ui.home.view.AlbumScreen
import com.example.connex.ui.home.view.FriendsAddScreen
import com.example.connex.ui.home.view.FriendsRemoveScreen
import com.example.connex.ui.home.view.FriendsScreen
import com.example.connex.ui.home.view.HomeScreen
import com.example.connex.ui.home.view.MypageScreen
import com.example.connex.ui.login.view.LoginCompleteScreen
import com.example.connex.ui.login.view.LoginScreen
import com.example.connex.ui.login.view.ProfileInitScreen
import com.example.connex.utils.Constants
import com.example.connex.utils.Constants.FRIEND_ADD_ROUTE
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


fun NavGraphBuilder.loginGraph(applicationState: ApplicationState) {
    navigation(startDestination = SIGNUP_START_ROUTE, route = LOGIN_GRAPH) {
        composable(SIGNUP_START_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = LOGIN_GRAPH
            )
            LoginScreen(
                applicationState = applicationState,
                loginViewModel = hiltViewModel(backStackEntry)
            )
        }
        composable(SIGNUP_PROFILE_INIT_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = LOGIN_GRAPH
            )
            ProfileInitScreen(
                applicationState = applicationState,
                loginViewModel = hiltViewModel(backStackEntry)
            )
        }
        composable("${SIGNUP_COMPLETE_ROUTE}/{userId}/{name}") { entry ->
            LoginCompleteScreen(
                applicationState = applicationState,
                userId = entry.arguments?.getString("userId") ?: "111_111",
                name = entry.arguments?.getString("name") ?: "새싹"
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.initSettingGraph(applicationState: ApplicationState) {
    navigation(startDestination = FRIEND_SYNC_ROUTE, route = INIT_SETTING_GRAPH) {
        composable("${FRIEND_SYNC_ROUTE}/{userId}/{name}") { entry ->
            FriendSyncScreen(
                applicationState = applicationState,
                name = entry.arguments?.getString("name") ?: "새싹",
                userId = entry.arguments?.getString("userId")?.toLong() ?: 111_111L,
            )
        }
        composable("${FRIEND_SYNC_COMPLETE_ROUTE}/{userId}/{name}") { entry ->
            FriendSyncCompleteScreen(
                applicationState = applicationState,
                name = entry.arguments?.getString("name") ?: "새싹",
                userId = entry.arguments?.getString("userId")?.toLong() ?: 111_111L,
            )
        }
    }
}

fun NavGraphBuilder.homeGraph(applicationState: ApplicationState) {
    navigation(startDestination = HOME_ROUTE, route = HOME_GRAPH) {
        composable(Screen.Home.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = HOME_GRAPH
            )
            HomeScreen(applicationState = applicationState)
        }
        composable(Screen.Friends.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = HOME_GRAPH
            )
            FriendsScreen(
                friendsViewModel = hiltViewModel(backStackEntry),
                applicationState = applicationState
            )
        }
        composable(Screen.Album.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = HOME_GRAPH
            )
            AlbumScreen(applicationState = applicationState)
        }
        composable(Screen.Mypage.route) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = HOME_GRAPH
            )
            MypageScreen(applicationState = applicationState)
        }

        composable(FRIEND_REMOVE_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = HOME_GRAPH
            )
            FriendsRemoveScreen(
                friendsViewModel = hiltViewModel(backStackEntry),
                applicationState = applicationState
            )
        }
        composable(FRIEND_ADD_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = HOME_GRAPH
            )
            FriendsAddScreen(
                friendsViewModel = hiltViewModel(backStackEntry),
                applicationState = applicationState
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.creatingAlbumGraph(applicationState: ApplicationState) {
    navigation(
        route = Constants.ALBUM_CREATE_START_GRAPH,
        startDestination = Constants.ALBUM_CREATING_ROUTE
    ) {
        composable(Constants.ALBUM_CREATING_ROUTE) { entry ->
            CreatingAlbumScreen(applicationState = applicationState)
        }
        composable(Constants.ALBUM_CREATING_COMPLETE_ROUTE) { entry ->
            CreatingAlbumCompleteScreen(applicationState = applicationState)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.albumGraph(applicationState: ApplicationState) {
    navigation(
        route = Constants.ALBUM_INFO_GRAPH,
        startDestination = Constants.ALBUM_SETTING_ROUTE
    ) {
        composable("${Constants.ALBUM_INFO_PHOTO_LIST_ROUTE}/{albumId}") { entry ->
            PhotosScreen(
                applicationState = applicationState,
                albumId = entry.arguments?.getString("albumId")
            )
        }
        composable("${Constants.ALBUM_INFO_PHOTO_ROUTE}/{photoUrl}/{photoId}") { entry ->
            PhotoOfAlbumScreen(
                applicationState = applicationState,
                photo = entry.arguments?.getString("photoUrl"),
                photoId = entry.arguments?.getString("photoId")
            )
        }
        composable("${Constants.ALBUM_INFO_PHOTO_COMMENT_ROUTE}/{photoUrl}/{photoId}") { entry ->
            PhotoCommentScreen(
                photo = entry.arguments?.getString("photoUrl"),
                photoId = entry.arguments?.getString("photoId")
            )
        }
        composable("${Constants.ALBUM_INFO_PHOTO_ADD_ROUTE}/{albumId}/{currentFileSize}/{totalFileSize}") { entry ->
            val currentFSize =
                (entry.arguments?.getString("currentFileSize") ?: "").toDouble().toLong()
            val totalFSize = (entry.arguments?.getString("totalFileSize") ?: "").toDouble().toLong()
            AddPictureScreen(
                applicationState = applicationState,
                albumId = entry.arguments?.getString("albumId") ?: "",
                currentFSize = currentFSize,
                totalFSize = totalFSize,
            )
        }
    }
}

fun NavGraphBuilder.albumSettingGraph(applicationState: ApplicationState) {
    navigation(
        route = Constants.ALBUM_SETTING_GRAPH,
        startDestination = Constants.ALBUM_SETTING_ROUTE
    ) {
        composable(route = "${Constants.ALBUM_SETTING_ROUTE}/{albumId}") { entry ->
            AlbumSettingScreen(
                applicationState = applicationState,
                albumId = entry.arguments?.getString("albumId")
            )
        }
        composable(route = "${Constants.ALBUM_INFO_ROUTE}/{albumId}") { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = applicationState.navController,
                graph = Constants.ALBUM_SETTING_GRAPH
            )
            AlbumInfoScreen(
                applicationState = applicationState,
                albumInfoViewModel = hiltViewModel(backStackEntry),
                albumId = entry.arguments?.getString("albumId")
            )
        }
        composable(route = Constants.ALBUM_INFO_THUMBNAIL_ROUTE) { entry ->
            ThumbnailSettingScreen(applicationState = applicationState)
        }
        composable(route = Constants.ALBUM_INFO_MEMBER_ROUTE) { entry ->
            MembersOfAlbumScreen(applicationState = applicationState)
        }
        composable(route = Constants.ALBUM_INFO_MEMBER_GRANTED_SETTING_ROUTE) { entry ->
            MemberGrantedSettingScreen(applicationState = applicationState)
        }
        composable(route = Constants.ALBUM_INFO_MEMBER_ADD_ROUTE) { entry ->
            AddMemberToAlbumScreen(applicationState = applicationState)
        }
    }
}

fun NavGraphBuilder.recommendedFriendGraph(applicationState: ApplicationState) {
    navigation(
        route = Constants.RECOMMENDED_CONTACT_GRAPH,
        startDestination = Constants.RECOMMENDED_CONTACT_RESULT_ROUTE
    ) {
        composable(route = Constants.RECOMMENDED_CONTACT_LOGGING_ROUTE) { entry ->
            RecommendContactLoadingScreen()
        }
        composable(route = Constants.RECOMMENDED_CONTACT_RESULT_ROUTE) { entry ->
            RecommendContactResultScreen()
        }
    }


}


@Composable
fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}