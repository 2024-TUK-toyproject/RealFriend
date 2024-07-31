package com.example.connex.ui.login.view

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.connex.BuildConfig
import com.example.connex.ui.component.ArrowBackIcon
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.PictureChoiceDialog
import com.example.connex.ui.component.UserNameTextField
import com.example.connex.ui.component.util.addFocusCleaner
import com.example.connex.ui.domain.cameraLauncher
import com.example.connex.ui.domain.createImageFile
import com.example.connex.ui.domain.getImageUri
import com.example.connex.ui.domain.takePhotoFromAlbumIntent
import com.example.connex.ui.domain.takePhotoFromAlbumLauncher
import com.example.connex.ui.login.LoginViewModel
import com.example.connex.ui.theme.MainBlue
import com.example.connex.ui.theme.Typography
import com.example.connex.utils.Constants
import com.example.connex.utils.allDelete
import java.io.File
import java.util.Objects

@Composable
fun ProfileInitScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val context = LocalContext.current
    var image by remember { mutableStateOf(Uri.EMPTY) }
    var isShowDialog by remember { mutableStateOf(false) }
    var galleryImageUri by remember { mutableStateOf(Uri.EMPTY) }

    val takePhotoFromAlbumLauncher = takePhotoFromAlbumLauncher{
        image = it
        isShowDialog = false
    }
    val cameraLauncher = cameraLauncher(uri = galleryImageUri) {
        image = it
        isShowDialog = false
    }

    var text by remember { mutableStateOf("") }

    val buttonEnabled by remember { derivedStateOf {text.isNotBlank()} }

    DisposableEffect(Unit) {
        galleryImageUri = context.getImageUri()
        onDispose {
            val path = "/storage/emulated/0/Android/data/${BuildConfig.APPLICATION_ID}/cache/"
            val cashFile = File(path)
            cashFile.allDelete()
        }
    }


    if (isShowDialog) {
        PictureChoiceDialog(
            onClose = { isShowDialog = false },
            onClick1 = { cameraLauncher.launch(galleryImageUri) },
            onClick2 = {takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)}
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        Spacer(modifier = Modifier.height(84.dp - statusBarPadding))
        ArrowBackIcon(modifier = Modifier.padding(start = 24.dp)) {
            navController.popBackStack()
        }
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "사용할 프로필을 \n설정해 주세요.",
            style = Typography.titleMedium,
            modifier = Modifier.padding(horizontal = 28.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .width(104.dp)
                    .height(100.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    shape = CircleShape,
                    border = BorderStroke(width = 2.dp, color = MainBlue),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = image),
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
            Spacer(modifier = Modifier.height(72.5.dp))

            UserNameTextField(
                modifier = Modifier.width(200.dp),
                text = text,
                updateText = { if (it.length <= 10) { text = it } }
            ) {
                focusManager.clearFocus()
            }
        }


        GeneralButton(
            modifier = Modifier
                .height(55.dp)
                .padding(horizontal = 24.dp), text = "확인", enabled = buttonEnabled
        ) {
            navController.navigate(Constants.SIGNUP_COMPLETE_ROUTE)
        }
        Spacer(modifier = Modifier.height(80.dp))
    }

}