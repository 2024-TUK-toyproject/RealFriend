package com.example.connex.ui.home.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.home.TestViewModel
import com.example.connex.utils.Constants

@Composable
fun MypageScreen(testViewModel: TestViewModel = hiltViewModel(), applicationState: ApplicationState) {
    Button(onClick = { testViewModel.logout{applicationState.navigatePopBackStack(Constants.SIGNUP_START_ROUTE)} }) {
        Text(text = "로그아웃")
    }
}