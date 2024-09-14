package com.example.connex.ui.album_create.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.connex.ui.album_create.AlbumCreatingViewModel
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.rememberNavControllerBackEntry
import com.example.connex.utils.Constants

@Composable
fun CreatingAlbumNavHost(albumCreatingViewModel: AlbumCreatingViewModel, navController: NavHostController, applicationState: ApplicationState) {

    NavHost(navController = navController, startDestination = Constants.ALBUM_CREATING_GRAPH ) {
        navigation(route = Constants.ALBUM_CREATING_GRAPH, startDestination = Constants.ALBUM_CREATING_SELECT_FRIEND_ROUTE) {
            composable(route = Constants.ALBUM_CREATING_SELECT_FRIEND_ROUTE) {
                FriendSelectScreen(albumCreatingViewModel = albumCreatingViewModel) {applicationState.showSnackbar(it)}
            }
            composable(route = Constants.ALBUM_CREATING_SELECT_BACKGROUND_ROUTE) {
                BackgroundSelectScreen()
            }
            composable(route = Constants.ALBUM_CREATING_SETTING_NAME_ROUTE) {
                AlbumNameSettingScreen(albumCreatingViewModel = albumCreatingViewModel)
            }
        }
    }
}