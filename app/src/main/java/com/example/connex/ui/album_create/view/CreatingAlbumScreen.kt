package com.example.connex.ui.album_create.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.connex.ui.album_create.AlbumCreatingViewModel
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Gray300
import com.example.connex.utils.Constants

@Composable
fun CreatingAlbumScreen(albumCreatingViewModel: AlbumCreatingViewModel = hiltViewModel(),  applicationState: ApplicationState) {

    val navController = rememberNavController()


    var current by remember {
        mutableIntStateOf(1)
    }


    Scaffold(
        topBar = { BackArrowAppBar(text = "") {creatingAlbumPopBackStack(navController, applicationState) { current = it }} },
        snackbarHost = { SnackbarHost(applicationState.snackbarHostState) },
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) { innnerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innnerPadding)) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(innnerPadding)) {
                ColumnSpacer(height = 32.dp)
                Text(text = buildAnnotatedString {
                    append("$current")
                    withStyle(SpanStyle(color = Gray300)) {
                        append(" / 3")
                    }
                }, style = Body1Semibold, modifier = Modifier.padding(horizontal = 24.dp))
                ColumnSpacer(height = 12.dp)
                CreatingAlbumNavHost(albumCreatingViewModel = albumCreatingViewModel, navController = navController, applicationState = applicationState)
            }
            GeneralButton(modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                .height(51.dp)
                .align(Alignment.BottomCenter), text = "다음", enabled = true) {
                creatingAlbumNavigate(albumCreatingViewModel, navController, applicationState) { current = it }
            }
        }
    }
}

fun creatingAlbumNavigate(albumCreatingViewModel: AlbumCreatingViewModel, navController: NavHostController, applicationState: ApplicationState, currentUpdate: (Int) -> Unit) {
    when(navController.currentBackStackEntry?.destination?.route) {
        Constants.ALBUM_CREATING_SELECT_FRIEND_ROUTE -> {
            albumCreatingViewModel.fetchCreateAlbum(
                onSuccess = {
                    currentUpdate(2)
                    navController.navigate(Constants.ALBUM_CREATING_SELECT_BACKGROUND_ROUTE)
            }) { applicationState.showSnackbar("인터넷이 연결이 되어 있지 않습니다.") }
        }
        Constants.ALBUM_CREATING_SELECT_BACKGROUND_ROUTE -> {
            currentUpdate(3)
            navController.navigate(Constants.ALBUM_CREATING_SETTING_NAME_ROUTE)
        }
        Constants.ALBUM_CREATING_SETTING_NAME_ROUTE -> {
            albumCreatingViewModel.fetchUpdateAlbumThumbnail(
                onSuccess = {
                    applicationState.navigate(Constants.ALBUM_CREATING_COMPLETE_ROUTE)
                }
            ) { applicationState.showSnackbar("인터넷이 연결이 되어 있지 않습니다.") }
        }
    }
}

fun creatingAlbumPopBackStack(navController: NavHostController, applicationState: ApplicationState, currentUpdate: (Int) -> Unit) {
    when(navController.currentBackStackEntry?.destination?.route) {
        Constants.ALBUM_CREATING_SELECT_FRIEND_ROUTE -> {
            applicationState.popBackStack()
        }
        Constants.ALBUM_CREATING_SELECT_BACKGROUND_ROUTE -> {
            currentUpdate(1)
            navController.popBackStack()
        }
        Constants.ALBUM_CREATING_SETTING_NAME_ROUTE -> {
            currentUpdate(2)
            navController.popBackStack()
        }
    }
}