package com.example.connex.ui.albumphoto.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.albumphoto.PhotoInfoViewModel
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.BottomNavItem
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.General4Button
import com.example.connex.ui.component.PhotoCard
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcCalendar
import com.example.connex.ui.svg.iconpack.IcFile
import com.example.connex.ui.svg.iconpack.IcImage
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray600
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.White
import com.example.connex.utils.Constants
import com.example.connex.utils.setMemorySizeAndUnit

// 나중에 ViewModel로 옮겨
sealed class PhotoOfAlbumScreenState(open val title: String) {
    data class Default(override val title: String = "") : PhotoOfAlbumScreenState(title)
    data class Comment(override val title: String = "댓글") : PhotoOfAlbumScreenState(title)
    data class DetailInfo(override val title: String = "상세정보") : PhotoOfAlbumScreenState(title)
}

@Composable
fun PhotoOfAlbumScreen(
    photoInfoViewModel: PhotoInfoViewModel = hiltViewModel(),
    applicationState: ApplicationState,
    photo: String?,
    photoId: String?,
) {
    Log.d("daeyoung", "picture: $photo")

    var photoOfAlbumScreenState by remember {
        mutableStateOf<PhotoOfAlbumScreenState>(PhotoOfAlbumScreenState.Default())
    }
    var isBottomBarVisible by remember {
        mutableStateOf(true)
    }

    val imageSize by animateFloatAsState(
//        targetValue = if (isKeyboardOpen == KeyboardStatus.Opened) 0.223f else 0.277f,
        targetValue = when (photoOfAlbumScreenState) {
            is PhotoOfAlbumScreenState.DetailInfo -> 0.85f
            is PhotoOfAlbumScreenState.Comment -> 0.377f
            else -> 1f
        },

        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        )
    )

    val photoUiState by photoInfoViewModel.photoInfo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        photoId?.let { photoInfoViewModel.fetchReadPhotoInfo(it) }
    }
    ManageBottomBarState(photoOfAlbumScreenState = photoOfAlbumScreenState) {
        isBottomBarVisible = it
    }
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackArrowAppBar(text = photoOfAlbumScreenState.title) {}
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                PhotoCard(
                    modifier = Modifier
//                    .align(Alignment.Center)
                        .fillMaxWidth(imageSize)
                        .aspectRatio(1f),
                    picture = photo
                )
                ColumnSpacer(height = 32.dp)
                if (photoOfAlbumScreenState is PhotoOfAlbumScreenState.DetailInfo) {
                    Box() {
                        DetailInfoBox(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth(),
                            profileImage = photoUiState.uploadUserProfile,
                            fileName = photoUiState.name,
                            userName = photoUiState.uploadUser,
                            uploadDate = photoUiState.uploadDate,
                            date = photoUiState.date,
                            time = photoUiState.time,
                            fileSize = photoUiState.usage.toLong()
                        ) {
                            photoOfAlbumScreenState = PhotoOfAlbumScreenState.Default()
                        }
                    }
                }
                if (photoOfAlbumScreenState is PhotoOfAlbumScreenState.Comment) {
                    CommentBottomSheet()
                }
            }
        }

        PhotoOfAlbumBottomBar(
            isVisible = isBottomBarVisible,
            onFavorite = { /*TODO*/ },
            onChat = {
//                photoOfAlbumScreenState = PhotoOfAlbumScreenState.Comment()
                if (photo != null && photoId != null) {
                    applicationState.navigateEncodingUrl(Constants.ALBUM_INFO_PHOTO_COMMENT_ROUTE, photo, photoId)
                }
            },
            onDetailInfo = {
                photoOfAlbumScreenState = PhotoOfAlbumScreenState.DetailInfo()
            },
            onDelete = {}
        )
    }
}



@Composable
fun BoxScope.PhotoOfAlbumBottomBar(
    isVisible: Boolean = false,
    onFavorite: () -> Unit,
    onChat: () -> Unit,
    onDetailInfo: () -> Unit,
    onDelete: () -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(color = Color.Transparent),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = Gray100,
                        start = Offset.Zero,
                        end = Offset(x = size.width, y = 0f),
                        strokeWidth = 1f
                    )
                }
                .background(color = Color.White)
                .padding(horizontal = 38.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(icon = Icons.Outlined.FavoriteBorder, text = "좋아요") { onFavorite() }
            BottomNavItem(icon = Icons.Outlined.ChatBubbleOutline, text = "댓글") { onChat() }
            BottomNavItem(icon = Icons.Outlined.Info, text = "상세정보") { onDetailInfo() }
            BottomNavItem(icon = Icons.Outlined.Delete, text = "삭제하기") { onDelete() }
        }
    }
}

@Composable
fun ManageBottomBarState(
    photoOfAlbumScreenState: PhotoOfAlbumScreenState,
    bottomBarVisibleState: (Boolean) -> Unit,

    ) {
    when (photoOfAlbumScreenState) {
        PhotoOfAlbumScreenState.Default() -> bottomBarVisibleState(true)
        else -> bottomBarVisibleState(false)
    }
}

@Composable
fun DetailInfoBox(
    modifier: Modifier = Modifier,
    profileImage: String,
    fileName: String,
    userName: String,
    uploadDate: String,
    date: String,
    time: String,
    fileSize: Long,
    onClose: () -> Unit,
) {
    val uploadPersonStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.4.sp,
        fontWeight = FontWeight.SemiBold,
        color = Gray800,
    )

    val textStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 19.6.sp,
        fontWeight = FontWeight.Normal,
        color = Gray800,
    )
    val (pictureFSize, pictureFUnit) = setMemorySizeAndUnit(fileSize)

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .shadow(
                    shape = RoundedCornerShape(size = 12.dp),
                    elevation = 6.dp,
//                spotColor = Color(0x14000000),
//                ambientColor = Color(0x14000000)
                    spotColor = Gray500,
                    ambientColor = Gray500
                )
                .background(color = White, shape = RoundedCornerShape(size = 12.dp))
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Card(modifier = Modifier.size(40.dp), shape = CircleShape) {
                    Image(
                        painter = rememberAsyncImagePainter(model = profileImage),
                        contentDescription = "image_profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                RowSpacer(width = 10.dp)
                Text(
                    text = userName,
                    style = uploadPersonStyle,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                RowSpacer(width = 10.dp)
                Text(
                    text = "(업로드 시간: $uploadDate)",
                    style = textStyle,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
            ColumnSpacer(height = 16.dp)
            HorizontalDivider(Modifier.fillMaxWidth(), color = Gray100, thickness = 0.5.dp)
            ColumnSpacer(height = 16.dp)
            IconTextRow(icon = IconPack.IcCalendar, text = date)
            ColumnSpacer(height = 10.dp)
            IconTextRow(icon = Icons.Outlined.AccessTime, text = time)
            ColumnSpacer(height = 10.dp)
            IconTextRow(icon = IconPack.IcImage, text = fileName)
            ColumnSpacer(height = 10.dp)
            IconTextRow(icon = IconPack.IcFile, text = "${pictureFSize} $pictureFUnit")

//            IconTextRow(icon = IconPack.IcLocation01, text = "위치")

        }
        ColumnSpacer(height = 16.dp)
        General4Button(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth(), text = "닫기"
        ) {
            onClose()
        }
    }
}

@Composable
fun IconTextRow(icon: ImageVector, text: String) {
    val textStyle = TextStyle(
        fontSize = 13.sp,
        lineHeight = 18.2.sp,
        fontWeight = FontWeight.Normal,
        color = Gray600,
    )
    Row {
        Icon(
            imageVector = icon,
            contentDescription = "ic__",
            modifier = Modifier.size(18.dp),
        )
        RowSpacer(width = 10.dp)
        Text(text = text, style = textStyle)
    }
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun CommentBottomSheet(modifier: Modifier = Modifier) {

    val sheetState = io.morfly.compose.bottomsheet.material3.rememberBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        defineValues = {
            // Bottom sheet height is 100 dp.
//            SheetValueSheetValue.Collapsed at height(100.dp)
            // Bottom sheet offset is 60%, meaning it takes 40% of the screen.
            SheetValue.PartiallyExpanded at offset(percent = 60)
            // Bottom sheet height is equal to its content height.
            SheetValue.Expanded at contentHeight
        }
    )

    val state = rememberModalBottomSheetState(
        confirmValueChange = { sheetValue ->
            when (sheetValue) {
                SheetValue.Hidden -> false
                SheetValue.Expanded -> true
                SheetValue.PartiallyExpanded -> true
            }
        }
    )
    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(1f),
        onDismissRequest = {},
        sheetState = state,
        scrimColor = Color.Transparent,
//        tonalElevation = 20.dp,
        shape = RoundedCornerShape(topStart = 13.dp, topEnd = 13.dp),
        containerColor = Color.White,
        contentColor = Gray600,
//        modifier = Modifier.shadow(elevation = 20.dp, spotColor = Color(0x1F000000), ambientColor = Color(0x1F000000)),
    ) {
        Text(text = "아직 댓글이 없습니다.")
    }
}