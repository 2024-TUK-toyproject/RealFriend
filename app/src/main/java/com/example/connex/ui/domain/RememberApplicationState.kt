package com.example.connex.ui.domain

import androidx.compose.material.ScaffoldState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberApplicationState(
    bottomBarState: MutableState<Boolean> = mutableStateOf(false),
    navController: NavHostController = rememberNavController(),
//    scaffoldState: ScaffoldState = rememberScaffoldState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
//    uiController: SystemUiController = rememberSystemUiController(),
//    cameraPositionState: CameraPositionState = rememberCameraPositionState {},
) = remember(Unit) {
    ApplicationState(
        bottomBarState,
        navController,
        snackbarHostState,
//        scaffoldState,
//        cameraPositionState,
//        uiController,
        coroutineScope,
    )
}