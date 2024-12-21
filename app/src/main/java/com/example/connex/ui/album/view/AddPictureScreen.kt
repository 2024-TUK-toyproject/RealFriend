package com.example.connex.ui.album.view

import android.net.Uri
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.album.AddPictureViewModel
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.HorizontalGrayDivider
import com.example.connex.ui.component.PlusCardButton
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.domain.takeMultiPhotoFromAlbumLauncher
import com.example.connex.ui.theme.BackgroundGray
import com.example.connex.ui.theme.Body1SemiBold
import com.example.connex.ui.theme.Body2SemiBold
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.PrimaryBlue3
import com.example.connex.ui.theme.Text14ptMedium
import com.example.connex.ui.theme.Text14ptRegular
import com.example.connex.ui.theme.White
import com.example.connex.utils.setMemorySizeAndUnit
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddPictureScreen(
    addPictureViewModel: AddPictureViewModel = hiltViewModel(),
    applicationState: ApplicationState,
    albumId: String,
    currentFSize: Long,
    totalFSize: Long,
//    currentFSize2: String,
//    totalFSize2: String,
) {
    Log.d("albumInfo", "${currentFSize}")
    Log.d("albumInfo", "${totalFSize}")
    val maxSelectCount = 10

    val takePhotoFromAlbumLauncher = takeMultiPhotoFromAlbumLauncher(maxSelectCount) {
        addPictureViewModel.updateImageUri(it)
    }

    val addPictureUiState by addPictureViewModel.imageUrl.collectAsStateWithLifecycle()

    val btnEnabled by remember {
        derivedStateOf { addPictureUiState.isNotEmpty() }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding(),
        topBar = { BackArrowAppBar(text = "사진 업로드") { applicationState.popBackStack() } },
        snackbarHost = { applicationState.snackbarHostState },
        bottomBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.White)
                .drawBehind {
                    drawLine(
                        color = Gray100,
                        start = Offset.Zero,
                        end = Offset(size.width, 0f)
                    )
                }
                .padding(horizontal = 24.dp, vertical = 14.dp)
            ) {
                GeneralButton(
                    modifier = Modifier.fillMaxHeight(),
                    text = "업로드 하기",
                    enabled = btnEnabled
                ) {
                    addPictureViewModel.fetchUploadPhotos(albumId) {
                        applicationState.showSnackbar("사진 업로드에 성공했습니다.")
                        applicationState.popBackStack()
                    }
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(innerPadding)
        ) {
            ColumnSpacer(height = 24.dp)
            SelectPhotoArea(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(top = 20.dp, bottom = 24.dp),
                maxCount = maxSelectCount,
                count = addPictureUiState.size,
                images = addPictureUiState.map { it.uri },
                addPicture = {
                    takePhotoFromAlbumLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) { addPictureViewModel.deleteImage(it) }
            ColumnSpacer(height = 24.dp)
            CurrentStorageSizeArea(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(top = 20.dp),
                maxFileSize = totalFSize,
                currentFileSize = currentFSize
            )
            ColumnSpacer(height = 24.dp)

            FutureStorageSizeArea(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(top = 20.dp, bottom = 24.dp),
                maxFileSize = totalFSize,
                // TODO(현재 파일 크기 더해야 함)
                currentFileSize = currentFSize + addPictureUiState.sumOf { it.fileSize }
            )
        }
    }

}

@Composable
fun SelectPhotoArea(
    modifier: Modifier = Modifier,
    maxCount: Int,
    count: Int,
    images: List<Uri>,
    addPicture: () -> Unit,
    deletePicture: (Uri) -> Unit,
) {

    Column(modifier) {
        Text(
            text = buildAnnotatedString {
                append("선택한 사진 ")
                withStyle(Body2SemiBold.toSpanStyle()) {
                    append("(${count}/${maxCount})")
                }

            },
            modifier = Modifier.padding(start = 24.dp),
            style = Body1SemiBold,
            color = Gray900
        )

        ColumnSpacer(height = 16.dp)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 24.dp)
        ) {
            item {
                PlusCardButton(size = 80.dp, shape = RoundedCornerShape(11.dp)) {
                    addPicture()
                }
            }
            items(images) { image ->
                SelectPhotoCard(image = image) {
                    deletePicture(image)
                }
            }
        }
    }
}

@Composable
fun SelectPhotoCard(image: Uri, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .aspectRatio(1f)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(11.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF2F3F7),
                contentColor = PrimaryBlue3
            )
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = "image_add_photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Card(
            modifier = Modifier
                .fillMaxSize(0.26f)
                .align(Alignment.TopEnd)
                .offset(6.dp, -6.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF555555),
                contentColor = White
            ),
            onClick = { onDelete() }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "ic_close",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(0.7f)
                )
            }
        }
    }
}

@Composable
fun CurrentStorageSizeArea(
    modifier: Modifier = Modifier,
    maxFileSize: Long,
    currentFileSize: Long,
) {
    val (maxFSize, maxFileUnit) = setMemorySizeAndUnit(maxFileSize)
    val (currentFSize, currentFileUnit) = setMemorySizeAndUnit(currentFileSize)
    Column(modifier) {
        Text(
            text = "현재 앨범 저장 공간",
            modifier = Modifier.padding(start = 24.dp),
            style = Body1SemiBold,
            color = Gray900
        )
        ColumnSpacer(height = 16.dp)
        TwoStickGraph(
            modifier = Modifier.padding(horizontal = 24.dp),
            color1 = BackgroundGray,
            color2 = PrimaryBlue2,
            sizePercent = currentFileSize / maxFileSize.toFloat()
        )
        ColumnSpacer(height = 8.dp)
        Text(
            text = "${currentFSize}$currentFileUnit / ${maxFSize}$maxFileUnit",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.End
        )
        ColumnSpacer(height = 24.dp)
        HorizontalGrayDivider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 24.dp, vertical = 20.dp)) {
            Text(text = "앨범 정리하러 가기", style = Body1SemiBold, color = Gray900)
            Text(text = "중복된 사진 0개 삭제 가능", style = Text14ptRegular, color = Color(0xFFACACAC))
        }
    }
}

@Composable
fun TwoStickGraph(
    modifier: Modifier = Modifier,
    height: Dp = 40.dp,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp),
    color1: Color,
    color2: Color,
    sizePercent: Float,
) {
    val percent = (sizePercent * 100).toInt()
    Box(
        modifier = modifier
            .clip(shape)
            .fillMaxWidth()
            .height(height)
            .background(color1)
    ) {
        Box(
            modifier = Modifier
                .clip(shape)
                .fillMaxHeight()
                .fillMaxWidth(sizePercent)
                .background(color2)
        ) {
            if (percent != 0) {
                Text(text = "${percent}%", color = White, style = Body3Medium, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun FutureStorageSizeArea(modifier: Modifier = Modifier, maxFileSize: Long, currentFileSize: Long) {
    val (maxFSize, maxFileUnit) = setMemorySizeAndUnit(maxFileSize)
    val (currentFSize, currentFileUnit) = setMemorySizeAndUnit(currentFileSize)
    val (remainFSize, remainFileUnit) = setMemorySizeAndUnit(maxFileSize - currentFileSize)

    val accentTextStyle = SpanStyle(
        color = PrimaryBlue3,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
    )

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "업로드 후 앨범 저장 공간",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp),
            style = Body1SemiBold,
            color = Gray900,
            textAlign = TextAlign.Start
        )
        ColumnSpacer(height = 16.dp)
        TwoStickGraph(
            modifier = Modifier.padding(horizontal = 24.dp),
            color1 = BackgroundGray,
            color2 = PrimaryBlue3,
            sizePercent = currentFileSize / maxFileSize.toFloat()
        )
        ColumnSpacer(height = 8.dp)
        Text(
            text = "${currentFSize}$currentFileUnit / ${maxFSize}$maxFileUnit",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.End
        )
        ColumnSpacer(height = 16.dp)
        Text(
            text = buildAnnotatedString {
                append("선택한 사진을 모두 업로드하면 ")
                withStyle(accentTextStyle) {
                    append("${remainFSize}$remainFileUnit")
                }
                append("가 남아요!")
            },
            style = Text14ptMedium
        )
    }
}