package com.example.connex.ui.albumphoto.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.albumphoto.PhotoCommentViewModel
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.PhotoCard
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.util.bottomNavigationPadding
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcSend
import com.example.connex.ui.theme.Body3Semibold
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray600
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.Text11ptRegular
import com.example.connex.ui.theme.Text14ptRegular
import com.example.connex.ui.theme.White
import com.example.connex.utils.Constants
import com.example.connex.utils.toFormatDate
import com.example.domain.entity.album.CommentInfo
import com.example.domain.model.ApiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoCommentScreen(
    photoCommentViewModel: PhotoCommentViewModel = hiltViewModel(),
    photo: String?,
    photoId: String?,
) {
    val bottomSheetState = rememberStandardBottomSheetState()
    val scope = rememberCoroutineScope()


    val photoCommentUiState by photoCommentViewModel.photoCommentUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        Log.d("daeyoung", "photoId: $photoId")
        photoCommentViewModel.fetchReadUserImage()
        photoId?.let { photoCommentViewModel.fetchReadAllCommentsUseCase(it) }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        if (photoCommentUiState.comments is ApiState.Success) {
            BottomSheetScaffold(
                sheetContent = {
                    Column(
                        Modifier
                            .fillMaxSize(),
                    ) {
                        (photoCommentUiState.comments as ApiState.Success<List<CommentInfo>>).data.forEach {
                            CommentItem(commentInfo = it)
                        }
                    }
                },
                sheetContainerColor = White,
                containerColor = White,
//            sheetDragHandle = null,
                topBar = { BackArrowAppBar(text = "댓글") {} },
                scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState),
                sheetShadowElevation = 6.dp,
                sheetPeekHeight = 500.dp // 최소 높이 설정
            ) { paddingValues ->
                // Main content
                Column(
                    Modifier
                        .statusBarsPadding()
                        .padding(paddingValues)
                        .padding(vertical = 8.dp)
                        .fillMaxSize(),
//                    .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
                ) {
                    PhotoCard(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        picture = photo
                    )
                }
            }
            CommentTextField(
                modifier = Modifier
                    .imePadding()
                    .background(Color.White)
                    .bottomNavigationPadding(true)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                text = photoCommentUiState.input
            ) {
                photoCommentViewModel.updateComment(it)
            }
        } else {
            CircularProgressIndicator()
        }

    }
}


@Composable
fun CommentTextField(modifier: Modifier = Modifier, text: String, updateText: (String) -> Unit) {
    val color = if (text.isEmpty()) Gray400 else Gray600

    BasicTextField(
        modifier = modifier.drawBehind {
            drawLine(
                color = Gray100,
                start = Offset.Zero,
                end = Offset(size.width, 0f)
            )
        },
        value = text,
        onValueChange = { updateText(it) },
        textStyle = Text14ptRegular.copy(color),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions { updateText("$text\n") },
        cursorBrush = SolidColor(color),
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileCard(size = 28.dp, image = Constants.DEFAULT_PROFILE)

            RowSpacer(width = 16.dp)
            Box(modifier = Modifier.weight(1f)) {
                innerTextField()
                if (text.isEmpty()) {
                    Text(text = "댓글을 남기기...", style = Text14ptRegular, color = color)
                }
            }
            RowSpacer(width = 10.dp)
            Icon(
                imageVector = IconPack.IcSend,
                contentDescription = "ic_done",
                modifier = Modifier.size(20.dp),
                tint = Gray400
            )
        }
    }
}

@Composable
fun CommentItem(commentInfo: CommentInfo) {
    val textStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 19.6.sp,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileCard(size = 32.dp, image = commentInfo.userProfile)
        RowSpacer(width = 12.dp)
        Column {
            Row {
                Text(text = commentInfo.userName, style = Body3Semibold, color = Gray600)
                RowSpacer(width = 5.dp)
                Text(
                    text = toFormatDate(date = commentInfo.date, time = commentInfo.time),
                    style = Text11ptRegular,
                    color = Gray400
                )
            }
            ColumnSpacer(height = 1.dp)
            Text(text = "여기 또 가고 싶다", style = textStyle, color = Gray800)
        }
    }
}

@Composable
fun ProfileCard(size: Dp, image: String) {
    Card(modifier = Modifier.size(size), shape = CircleShape) {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = "image_profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}