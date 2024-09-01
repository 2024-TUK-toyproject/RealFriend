package com.example.connex.ui.login.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.connex.BuildConfig
import com.example.connex.ui.component.ArrowBackIcon
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.PictureChoiceDialog
import com.example.connex.ui.component.UserNameTextField
import com.example.connex.ui.component.util.addFocusCleaner
import com.example.connex.ui.contentprovider.ComposeFileProvider
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.domain.keyboardAsState
import com.example.connex.ui.domain.model.KeyboardStatus
import com.example.connex.ui.domain.takePhotoFromAlbumIntent
import com.example.connex.ui.domain.takePhotoFromAlbumLauncher
import com.example.connex.ui.login.LoginViewModel
import com.example.connex.ui.theme.Heading1
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.utils.Constants
import com.example.connex.utils.allDelete
import java.io.File

@Composable
fun ProfileInitScreen(
    applicationState: ApplicationState,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val profileInitUiState by loginViewModel.profileInitUiState.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
//    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val context = LocalContext.current
    var image by remember { mutableStateOf(Uri.EMPTY) }
    var isShowDialog by remember { mutableStateOf(false) }
    var hasImage by remember { mutableStateOf(false) }

    val takePhotoFromAlbumLauncher = takePhotoFromAlbumLauncher {
        loginViewModel.updateImageUrl(it)
        isShowDialog = false
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        hasImage = it
        isShowDialog = false
    }

    val buttonEnabled by remember { derivedStateOf { profileInitUiState.name.isNotBlank() } }

    val isKeyboardOpen by keyboardAsState() // Keyboard.Opened or Keyboard.Closed

    val profileSize by animateFloatAsState(
//        targetValue = if (isKeyboardOpen == KeyboardStatus.Opened) 0.223f else 0.277f,
        targetValue = if (isKeyboardOpen == KeyboardStatus.Opened) 0.223f else 0.377f,

        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        )
    )

    val bottomPaddingSize by animateDpAsState(
        targetValue = if (isKeyboardOpen == KeyboardStatus.Opened) 0.dp else 40.dp,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        )
    )
    val heightSpacerSize by animateDpAsState(
        targetValue = if (isKeyboardOpen == KeyboardStatus.Opened) 20.dp else 50.dp,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        )
    )


//    Log.d("daeyoung", "isKeyboardOpen: $isKeyboardOpen")


    LaunchedEffect(hasImage) {
        if (hasImage) loginViewModel.updateImageUrl(image)
    }

    DisposableEffect(Unit) {
        onDispose {
            val path = "/storage/emulated/0/Android/data/${BuildConfig.APPLICATION_ID}/cache/"
            val cashFile = File(path)
            cashFile.allDelete()
        }
    }


    if (isShowDialog) {
        PictureChoiceDialog(
            onClose = { isShowDialog = false },
            onClick1 = {
                val uri = ComposeFileProvider.getImageUri(context)
                image = uri
                cameraLauncher.launch(uri)
            },
            onClick2 = { takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .padding(start = 24.dp, end = 24.dp, top = 40.dp, bottom = bottomPaddingSize)
            .imePadding()
    ) {
//        Spacer(modifier = Modifier.height(84.dp - statusBarPadding))
        ArrowBackIcon() {
            applicationState.popBackStack()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
//            androidx.compose.animation.AnimatedVisibility(
//                visible = isKeyboardOpen != KeyboardStatus.Opened,
//                enter = slideInVertically(initialOffsetY = {
//                    -it
//                }),
//                exit = slideOutVertically(targetOffsetY = {
//                    -it
//                })
//            ) {
//
//            }
            Text(
                text = "사용할 프로필을 \n설정해 주세요.",
                style = Heading1,
                modifier = Modifier.padding(top = 64.dp, bottom = 40.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(profileSize)
                        .aspectRatio(1.04f)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        shape = CircleShape,
                        border = BorderStroke(width = 2.dp, color = PrimaryBlue2),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = profileInitUiState.imageUrl),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    IconButton(
                        onClick = { isShowDialog = true },
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.BottomEnd)
                            .shadow(
                                elevation = 4.dp,
                                shape = CircleShape,
                                ambientColor = Color.Black.copy(0.16f)
                            )
                            .clip(CircleShape)
                            .background(Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFFC1C1C1)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(heightSpacerSize))

                UserNameTextField(
                    modifier = Modifier.width(200.dp),
                    text = profileInitUiState.name,
                    updateText = {
                        if (it.length <= 10) {
                            loginViewModel.updateName(it)
                        }
                    }
                ) {
                    focusManager.clearFocus()
                }
            }
        }

        GeneralButton(
            modifier = Modifier
                .height(55.dp),
            text = "확인", enabled = buttonEnabled
        ) {
            loginViewModel.fetchSignupProfileImage(onSuccess = { applicationState.navigate("${Constants.SIGNUP_COMPLETE_ROUTE}/${loginViewModel.userId}/${profileInitUiState.name}") }) {
                applicationState.showSnackbar(
                    "인터넷이 연결이 되어 있지 않습니다."
                )
            }
        }
    }

}