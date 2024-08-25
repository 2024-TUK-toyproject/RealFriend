package com.example.connex.ui.domain

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.example.connex.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class ApplicationState(
    val bottomBarState: MutableState<Boolean>,
    val navController: NavHostController,
//    val scaffoldState: ScaffoldState,
    val snackbarHostState: SnackbarHostState,
//    val cameraPositionState: CameraPositionState,
//    val systmeUiController: SystemUiController,
    private val coroutineScope: CoroutineScope,
) {

    fun changeBottomBarVisibility(visibility: Boolean) {
        bottomBarState.value = visibility
    }

    fun showSnackbar(message: String) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route)
    }

    fun navigatePopBackStack(previousRoute: String, route: String) {
        navController.navigate(route) {
            popUpTo(previousRoute) {
                inclusive = true
//                saveState = true
            }
//            launchSingleTop = true
//            restoreState = true
        }
    }
}