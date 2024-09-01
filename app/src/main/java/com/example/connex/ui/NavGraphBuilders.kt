package com.example.connex.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.example.connex.ui.album_create.view.CreatingAlbumScreen
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.friendinit.view.FriendSyncCompleteScreen
import com.example.connex.ui.friendinit.view.FriendSyncScreen
import com.example.connex.ui.notification.view.NotificationScreen
import com.example.connex.ui.splash.view.SplashScreen
import com.example.connex.utils.Constants
import com.example.connex.utils.Constants.ALBUM_CREATING_ROUTE

fun NavGraphBuilder.notificationComposable(applicationState: ApplicationState) {

    composable(
        route = "${Constants.NOTIFICATION_ROUTE}?initialPage={initialPage}",
        deepLinks = listOf(navDeepLink {
            uriPattern = "${Constants.DEEP_LINK_URI}${Constants.NOTIFICATION_ROUTE}/{initialPage}"
        }),
        arguments = listOf(navArgument("initialPage") { defaultValue = 0 }),
    ) { navBackStackEntry ->
        NotificationScreen(
            applicationState = applicationState,
            initialPage = navBackStackEntry.arguments!!.getInt("initialPage")
        )
    }
}

fun NavGraphBuilder.splashComposable(applicationState: ApplicationState) {
    composable(route = Constants.SPLASH_ROUTE) {
        SplashScreen(applicationState = applicationState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.creatingAlbumComposable(applicationState: ApplicationState) {
    composable(ALBUM_CREATING_ROUTE) { entry ->
        CreatingAlbumScreen(applicationState = applicationState)
    }
}