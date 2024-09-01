package com.example.connex.ui.album_create.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Gray300
import com.example.connex.utils.Constants

@Composable
fun CreatingAlbumScreen(applicationState: ApplicationState) {

    val navController = rememberNavController()

    Scaffold(
        topBar = { BackArrowAppBar(text = "") {creatingAlbumPopBackStack(navController, applicationState)} },
        snackbarHost = { SnackbarHost(applicationState.snackbarHostState) }
    ) { innnerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innnerPadding).navigationBarsPadding()) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innnerPadding)) {
                Text(text = buildAnnotatedString {
                    append("current")
                    withStyle(SpanStyle(color = Gray300)) {
                        append(" / 3")
                    }
                }, style = Body1Semibold, modifier = Modifier.padding(horizontal = 24.dp))
                ColumnSpacer(height = 12.dp)
                CreatingAlbumNavHost(navController = navController)
            }
            GeneralButton(modifier = Modifier.height(51.dp).padding(horizontal = 24.dp).align(Alignment.BottomCenter), text = "다음", enabled = true) {
                creatingAlbumNavigate(navController)
            }
        }
    }
}

fun creatingAlbumNavigate(navController: NavHostController) {
    when(navController.currentBackStackEntry?.destination?.route) {
        Constants.ALBUM_CREATING_SELECT_FRIEND_ROUTE -> {
            navController.navigate(Constants.ALBUM_CREATING_SELECT_BACKGROUND_ROUTE)
        }
        Constants.ALBUM_CREATING_SELECT_BACKGROUND_ROUTE -> {
            navController.navigate(Constants.ALBUM_CREATING_SETTING_NAME_ROUTE)
        }
        Constants.ALBUM_CREATING_SETTING_NAME_ROUTE -> {

        }
    }
}

fun creatingAlbumPopBackStack(navController: NavHostController, applicationState: ApplicationState) {
    when(navController.currentBackStackEntry?.destination?.route) {
        Constants.ALBUM_CREATING_SELECT_FRIEND_ROUTE -> {
            applicationState.popBackStack()
        }
        Constants.ALBUM_CREATING_SELECT_BACKGROUND_ROUTE -> {
            navController.popBackStack()
        }
        Constants.ALBUM_CREATING_SETTING_NAME_ROUTE -> {
            navController.popBackStack()
        }
    }
}