package com.example.connex.ui.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

@Stable
class ApplicationState(
    val bottomBarState: MutableState<Boolean>,
    val navController: NavHostController,
//    val scaffoldState: ScaffoldState,
//    val cameraPositionState: CameraPositionState,
//    val systmeUiController: SystemUiController,
//    private val coroutineScope: CoroutineScope,
) {

    fun changeBottomBarVisibility(visibility: Boolean) {
        bottomBarState.value = visibility
    }

//    fun showSnackbar(message: String) {
//        coroutineScope.launch {
//            scaffoldState.snackbarHostState.showSnackbar(message)
//        }
//    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route)
    }
}