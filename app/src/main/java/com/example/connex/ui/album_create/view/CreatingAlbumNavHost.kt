package com.example.connex.ui.album_create.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.connex.utils.Constants

@Composable
fun CreatingAlbumNavHost(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Constants.ALBUM_CREATING_SELECT_FRIEND_ROUTE ) {
        composable(route = Constants.ALBUM_CREATING_SELECT_FRIEND_ROUTE) {
            FriendSelectScreen()
        }
        composable(route = Constants.ALBUM_CREATING_SELECT_BACKGROUND_ROUTE) {
            BackgroundSelectScreen()
        }
        composable(route = Constants.ALBUM_CREATING_SETTING_NAME_ROUTE) {
            AlbumNameSettingScreen()
        }
    }
}