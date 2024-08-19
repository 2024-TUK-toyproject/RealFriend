package com.example.connex.ui.splash.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.connex.ui.splash.SplashViewModel
import com.example.connex.utils.Constants

@Composable
fun SplashScreen(splashViewModel: SplashViewModel = hiltViewModel(), navController: NavController) {

    LaunchedEffect(Unit) {
        splashViewModel.fetchAutoLogin(
            onSuccess = { navController.navigate(Constants.HOME_ROUTE) },
            onFail = { navController.navigate(Constants.SIGNUP_START_ROUTE) })
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "스플래쉬 스크린")
    }
}