package com.example.connex.ui.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberApplicationState(
    bottomBarState: MutableState<Boolean> = mutableStateOf(false),
    navController: NavHostController = rememberNavController(),
//    scaffoldState: ScaffoldState = rememberScaffoldState(),
//    coroutineScope: CoroutineScope = rememberCoroutineScope(),
//    uiController: SystemUiController = rememberSystemUiController(),
//    cameraPositionState: CameraPositionState = rememberCameraPositionState {},
) = remember(Unit) {
    ApplicationState(
        bottomBarState,
        navController,
//        scaffoldState,
//        cameraPositionState,
//        uiController,
//        coroutineScope,
    )
}