package com.example.connex.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.notification.view.NotificationScreen
import com.example.connex.ui.splash.view.SplashScreen
import com.example.connex.utils.Constants

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

