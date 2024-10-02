package com.example.connex.ui.album.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.album.PictureOfAlbumViewModel
import com.example.connex.ui.component.AlbumSelectModeBottomBar
import com.example.connex.ui.component.AppBarIcon
import com.example.connex.ui.component.CheckButton
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.SimpleCheckButton
import com.example.connex.ui.component.util.bottomBarsPadding
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.home.view.CreatingAlbumPlusCard
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Body2Semibold
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray600
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.PrimaryBlue3
import com.example.connex.utils.Constants
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState


@Composable
fun PicturesListScreen(
    applicationState: ApplicationState,
    pictureOfAlbumViewModel: PictureOfAlbumViewModel = hiltViewModel(),
) {
    val state = rememberCollapsingToolbarScaffoldState()
    var isSelectMode by remember { mutableStateOf(false) }

    val pictureUiState by pictureOfAlbumViewModel.pictures.collectAsStateWithLifecycle()

    BackHandler {
        if (isSelectMode) isSelectMode = false
        else applicationState.popBackStack()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
//            .padding(bottom = Constants.BottomNavigationHeight)
            .bottomBarsPadding(isSelectMode)
            .statusBarsPadding()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CollapsingToolbarScaffold(
                modifier = Modifier.fillMaxSize(),
                state = state,
                scrollStrategy = ScrollStrategy.EnterAlways,
                toolbar = {
                    if (isSelectMode) {
                        SelectModeAppBar(
                            albumName = "앨범이름",
                            selectedImageCount = pictureUiState.count{it.isSelected},
                            onClickAllSelect = { pictureOfAlbumViewModel.selectAllOfPicture() }) {
                            pictureOfAlbumViewModel.unselectAllOfPicture()
                        }
                    } else {
                        BackArrowAppBar2(
                            textComposable = {
                                PicturesListAppBarText(
                                    text = "앨범이름",
                                    userCount = 4,
                                    pictureCount = 8
                                )
                            },
                            trailingIcon = Icons.Rounded.MoreVert,
                            onBack = { applicationState.popBackStack() }) {

                        }
                    }
                }
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(9.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp),
                ) {
                    items(
                        count = pictureUiState.size,
                        key = { pictureUiState[it].id },
                        span = { GridItemSpan(if (it == 0) 2 else 1) }) { index ->
                        when (index) {
                            0 -> {
                                FirstPictureCard(
                                    modifier = Modifier.aspectRatio(1f),
                                    name = "올린 사람",
                                    time = "8h",
                                    profile = Constants.DEFAULT_PROFILE,
                                    image = "",
                                    isSelectMode = isSelectMode,
                                    isSelected = pictureUiState[index].isSelected,
                                    onCheck = {
                                        pictureOfAlbumViewModel.changeSelectedStateOfPicture(
                                            pictureUiState[index].id
                                        )
                                    },
                                    longPress = { if (!isSelectMode) isSelectMode = it }
                                ) {}
                            }

                            1 -> {
                                Column {
                                    TestCard(
                                        modifier = Modifier.aspectRatio(1f),
                                        image = "",
                                        isSelectMode = isSelectMode,
                                        isSelected = pictureUiState[index].isSelected,
                                        onCheck = {
                                            pictureOfAlbumViewModel.changeSelectedStateOfPicture(
                                                pictureUiState[index].id
                                            )
                                        },
                                        longPress = { if (!isSelectMode) isSelectMode = it }) {}
                                    Spacer(modifier = Modifier.height(9.dp))
                                    CreatingAlbumPlusCard(modifier = Modifier.aspectRatio(1f)) {}
                                }
                            }

                            else -> {
                                TestCard(
                                    modifier = Modifier.aspectRatio(1f),
                                    image = "",
                                    isSelectMode = isSelectMode,
                                    isSelected = pictureUiState[index].isSelected,
                                    onCheck = {
                                        pictureOfAlbumViewModel.changeSelectedStateOfPicture(
                                            pictureUiState[index].id
                                        )
                                    },
                                    longPress = { if (!isSelectMode) isSelectMode = it }
                                ) {}
                            }
                        }
                    }
                }

            }
            AlbumSelectModeBottomBar(isVisible = isSelectMode, onDownload = { /*TODO*/ }) {}
        }

    }
}

@Composable
fun TestCard(
    modifier: Modifier = Modifier,
    image: String,
    isSelectMode: Boolean,
    isSelected: Boolean = false,
    onCheck: () -> Unit,
    longPress: (Boolean) -> Unit,
    navigate: () -> Unit,
) {
    Card(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures(
                onLongPress = { longPress(true) },
                onTap = {
                    if (isSelectMode) onCheck()
                })
        },
//        onClick = { navigate() },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F3F7),
            contentColor = PrimaryBlue3
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            /* TODO: API 연동할 시 바꿀 것
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = "image_picture",
                modifier = Modifier.fillMaxSize()
            )
            */
            if (isSelectMode) {
                CheckButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 8.dp, y = 8.dp),
                    isChecked = isSelected,
                    color = Gray400
                )
            }
        }
    }
}

@Composable
fun FirstPictureCard(
    modifier: Modifier = Modifier,
    name: String,
    time: String,
    profile: String,
    image: String,
    isSelected: Boolean = false,
    onCheck: () -> Unit,
    longPress: (Boolean) -> Unit,
    isSelectMode: Boolean,
    navigate: () -> Unit,
) {
    Card(
        modifier = modifier.pointerInput(isSelectMode) {
            detectTapGestures(
                onLongPress = { longPress(true) },
                onTap = {
                    if (isSelectMode) onCheck()
                }
            )
        },
//        onClick = { navigate() },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F3F7),
            contentColor = PrimaryBlue3
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            /* TODO: API 연동할 시 바꿀 것
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = "image_picture",
                modifier = Modifier.fillMaxSize()
            )
            */
            if (isSelectMode) {
                CheckButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 8.dp, y = 8.dp),
                    isChecked = isSelected,
                    color = Gray400
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 16.dp)
            ) {
                Card(modifier = Modifier.size(36.dp), shape = CircleShape) {
                    Image(
                        painter = rememberAsyncImagePainter(model = profile),
                        contentDescription = "image_profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                RowSpacer(width = 8.dp)
                Column {
                    Text(text = name, style = Body2Semibold, color = Gray900)
                    Text(text = time, style = Body3Regular, color = Gray900)
                }
            }

        }
    }
}


@Composable
fun CollapsingToolbarScope.BackArrowAppBar2(
    textComposable: @Composable () -> Unit,
    trailingIcon: ImageVector,
    onBack: () -> Unit,
    onMenu: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    color = Gray100,
                    strokeWidth = 0.5f
                )
            }
            .padding(vertical = 10.dp, horizontal = 20.dp),
//            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppBarIcon(image = Icons.Rounded.ArrowBackIosNew) {
            onBack()
        }
        textComposable()
        AppBarIcon(image = trailingIcon) {
            onMenu()
        }
    }
}

@Composable
fun CollapsingToolbarScope.SelectModeAppBar(
    albumName: String,
    selectedImageCount: Int,
    onClickAllSelect: () -> Unit,
    onClickAllUnselect: () -> Unit,
) {

    val isCountText = remember(selectedImageCount) {
        derivedStateOf {
            if (selectedImageCount == 0) "항목 선택"
            else "${selectedImageCount}개 선택됨"
        }
    }

    var isAllChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    color = Gray100,
                    strokeWidth = 0.5f
                )
            }
            .padding(vertical = 10.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            SimpleCheckButton(
                isChecked = isAllChecked,
                color = Gray400,
                onClickChecked = {
                    isAllChecked = false
                    onClickAllUnselect()
                }) {
                isAllChecked = true
                onClickAllSelect()
            }
            ColumnSpacer(height = 2.dp)
            Text(text = "전체", fontSize = 10.sp, color = Gray600, lineHeight = 10.sp)
        }
        RowSpacer(width = 18.dp)
        Column {
            Text(text = albumName, style = Body3Regular, color = Gray500)
            Text(text = isCountText.value, style = Body1Semibold, color = Gray900)
        }
    }
}

@Composable
fun PicturesListAppBarText(text: String, userCount: Int, pictureCount: Int) {
    Column {
        Text(text = text, style = Body1Semibold, color = Gray900)
        Row {
            Text(text = "🗣️ $userCount", style = Body3Regular, color = Gray500)
            RowSpacer(width = 2.dp)
            Text(text = "📷 $pictureCount", style = Body3Regular, color = Gray500)
        }
    }
}