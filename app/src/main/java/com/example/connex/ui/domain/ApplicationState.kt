package com.example.connex.ui.domain

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.example.connex.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
    private var currentRoute: String? = Constants.SPLASH_ROUTE
    var deepLink: Uri? = null

    fun changeBottomBarVisibility(visibility: Boolean) {
        bottomBarState.value = visibility
    }

    fun showSnackbar(message: String) {
        coroutineScope.launch {
//            Log.d("daeYoung", "showSnackbar(), snackbarHostState: ${snackbarHostState}")
            snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }
    fun popBackStackHome() {
        navController.navigate(Constants.HOME_ROUTE) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigate(route: String) {
        navController.navigate(route)
        currentRoute = route
    }

    fun navigate(uri: Uri) {
        navController.navigate(uri)
    }

    fun navigateEncodingUrl(prefixUrl: String, encodeUrl: String, param: String) {
        val encodedUrl = URLEncoder.encode(encodeUrl, StandardCharsets.UTF_8.toString())
        navController.navigate("$prefixUrl/$encodedUrl/$param")
    }

    fun navigatePopBackStack(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
//            currentRoute?.let {
//                popUpTo(it) {
//                    inclusive = true
////                saveState = true
//                }
//            }
        }
////            launchSingleTop = true
////            restoreState = true
//        }
        currentRoute = route
    }
}