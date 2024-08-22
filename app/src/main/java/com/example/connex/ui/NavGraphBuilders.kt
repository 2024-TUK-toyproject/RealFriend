package com.example.connex.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.notification.view.NotificationScreen
import com.example.connex.ui.splash.view.SplashScreen
import com.example.connex.utils.Constants.NOTIFICATION_ROUTE
import com.example.connex.utils.Constants.SPLASH_ROUTE

fun NavGraphBuilder.notificationComposable(navController: NavController) {
    composable(route = NOTIFICATION_ROUTE) {
        NotificationScreen(navController)
    }
}

fun NavGraphBuilder.splashComposable(applicationState: ApplicationState) {
    composable(route = SPLASH_ROUTE) {
        SplashScreen(applicationState = applicationState)
    }
}