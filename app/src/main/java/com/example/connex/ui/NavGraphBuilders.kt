package com.example.connex.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.connex.ui.notification.view.NotificationScreen
import com.example.connex.utils.Constants.NOTIFICATION_ROUTE

fun NavGraphBuilder.notificationComposable(navController: NavController) {
    composable(route = NOTIFICATION_ROUTE) {
        NotificationScreen()
    }
}