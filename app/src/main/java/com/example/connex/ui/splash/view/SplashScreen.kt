package com.example.connex.ui.splash.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.splash.SplashViewModel
import com.example.connex.utils.Constants

@Composable
fun SplashScreen(splashViewModel: SplashViewModel = hiltViewModel(), applicationState: ApplicationState) {

    Log.d("navigate", "SplashScreen")
    applicationState.navController.currentBackStack.value.forEach {
        Log.d("navigate", "$it")

    }


    LaunchedEffect(Unit) {
        splashViewModel.fetchAutoLogin(
            onSuccess = { applicationState.navigatePopBackStack(Constants.SPLASH_ROUTE, Constants.HOME_ROUTE) },
            onFail = { applicationState.navigatePopBackStack(Constants.SPLASH_ROUTE, Constants.SIGNUP_START_ROUTE) },
            notResponse = {applicationState.showSnackbar("인터넷이 연결이 되어 있지 않습니다.")})
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "스플래쉬 스크린")
    }
}

