package com.example.connex.ui.home.view


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcNotification
import com.example.connex.ui.theme.Black
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.connex.ui.theme.Text16ptSemibold
import com.example.connex.ui.theme.White
import com.example.connex.utils.Constants
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.ExperimentalToolbarApi
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import com.example.connex.R
import com.example.connex.ui.component.SkyBlueBox

data class TempleAlbumData(
    val id: Long = 0,
    val image: String = " ",
    val album: String = "",
)

@Composable
fun AlbumScreen(modifier: Modifier = Modifier) {

    val state = rememberCollapsingToolbarScaffoldState()
    val scrollState = rememberLazyGridState()
    var headerHeight by remember { mutableIntStateOf(0) }
    var searchHeight by remember { mutableIntStateOf(0) }

    Surface(modifier = Modifier.fillMaxSize().navigationBarsPadding()) {
        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                Column(
                    modifier = Modifier
                        .padding(top = 85.dp)
                        .parallax(1f)
                ) {
//                ColumnSpacer(height = 28.dp)
                    SkyBlueBox(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        leadingImage = Icons.Rounded.Add,
                        leadingImageSize = 32.dp,
                        title = "우리만 볼 수 있는",
                        body = "공유 앨범 생성하기",
                        enabled = true,
                    ) {

                    }
                    ColumnSpacer(height = 24.dp)
                    NewUploadPictureSection(
                        listOf(
                            TempleAlbumData(id = 1),
                            TempleAlbumData(id = 2),
                            TempleAlbumData(id = 3),
                            TempleAlbumData(id = 4),
                            TempleAlbumData(id = 5),
                            TempleAlbumData(id = 6),
                        )
                    )
                    ColumnSpacer(height = 24.dp)
                }
                AlbumAppbar {}

            }
        ) {
            Column {
                AlbumSearchSection(category = "즐겨찾기 순", search = "") {}
                LazyVerticalGrid(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(start = 24.dp, end = 24.dp),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    items(11) {
                        AlbumCard(
                            modifier = Modifier
//                        .weight(1f)
                                .height(160.dp),
                            navigate = { /*TODO*/ }
                        ) {}
                    }
                }
            }

        }
    }
}

@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    name: String = "앨범이름",
    image: String = Constants.DEFAULT_PROFILE,
    userCount: Int = 4,
    imageCount: Int = 27,
    isFavorite: Boolean = false,
    navigate: () -> Unit,
    onFavorite: () -> Unit,
) {

    Box(
        modifier = modifier
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize(),
            onClick = { navigate() },
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = White
            )
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = "image_representative_album",
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .zIndex(1f)
                .align(Alignment.BottomStart)
                .offset(16.dp, (-16).dp)
        ) {
            Text(text = name, style = Text16ptSemibold, color = White)
            Row {
                Text(text = "🗣️ $userCount", style = Body3Regular, color = White)
                RowSpacer(width = 2.dp)
                Text(text = "📷 $userCount", style = Body3Regular, color = White)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp))
                .background(Black.copy(alpha = 0.2f))
        ) {}
    }
}


@Composable
fun CollapsingToolbarScope.AlbumAppbar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .drawBehind {
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    color = Gray100,
                    strokeWidth = 0.5f
                )
            }
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .pin(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "앨범", style = Head2Semibold, color = Gray900)
        Icon(
            imageVector = IconPack.IcNotification,
            contentDescription = "notification",
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable { onClick() },
            tint = Gray900
        )
    }
}

@Composable
fun AlbumCreateBox(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        onClick = {},
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF459AFE).copy(alpha = 0.12f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF459AFE),
                    contentColor = White
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "ic_add",
                    modifier = Modifier.fillMaxSize()
                )
            }
            RowSpacer(width = 24.dp)
            Column {
                Text(text = "우리만 볼 수 있는", style = Body3Medium, color = Gray500)
                ColumnSpacer(height = 4.dp)
                Text(text = "공유 앨범 생성하기", style = Body1Semibold, color = Gray900)
            }
        }
    }
}

@Composable
fun NewUploadPictureSection(newPictures: List<TempleAlbumData>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(
            text = "새로 올라온 사진 모아보기 📷",
            style = Body1Semibold,
            color = Gray900,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        ColumnSpacer(height = 16.dp)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = newPictures, key = { it.id }) { photo ->
                NewPictureCard()
            }
        }
    }
}

@Composable
fun NewPictureCard(image: String = Constants.DEFAULT_PROFILE, album: String = "누구다") {
    val gradientColor = listOf(Color(0xFF8FFFF1), Color(0xFFC9FF93))
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = CircleShape,
            border = BorderStroke(
                width = 2.dp,
                brush = Brush.horizontalGradient(colors = gradientColor)
            ),
            modifier = Modifier.size(60.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = "image_new_picture",
                modifier = Modifier.fillMaxSize()
            )
        }
        ColumnSpacer(height = 8.dp)
        Text(text = album, style = Body3Regular, color = Gray500)
    }
}

@Composable
fun AlbumSearchSection(category: String = "즐겨찾기순", search: String, updateSearch: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .onSizeChanged {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "공유 앨범 🖼️", style = Body1Semibold, color = Gray900)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = category, style = Body3Regular, color = Gray500)
                RowSpacer(width = 2.dp)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "ic_arrowDropdown",
                    tint = Gray500,
                    modifier = Modifier
                        .size(20.dp)
                        .noRippleClickable { }
                )
            }
        }
        ColumnSpacer(height = 20.dp)
        SearchTextField(
            modifier = Modifier.height(40.dp),
            padding = 24.dp to 10.dp,
            backgroundColor = Color(0xFFF2F4F8),
            text = search,
            placeholder = "앨범 이름이나 구성원으로 검색해 보세요.",
            updateText = { updateSearch(it) }) {
        }
        ColumnSpacer(height = 24.dp)
    }
}


@Composable
fun ParallaxEffect() {
    val state = rememberCollapsingToolbarScaffoldState()

    var enabled by remember { mutableStateOf(true) }

    Box {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            toolbarModifier = Modifier.background(Color.Transparent),
            enabled = enabled,
            toolbar = {
                // Collapsing toolbar collapses its size as small as the that of
                // a smallest child. To make the toolbar collapse to 50dp, we create
                // a dummy Spacer composable.
                // You may replace it with TopAppBar or other preferred composable.
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .parallax(0.5f)
                        .height(300.dp)
                        .graphicsLayer {
                            // change alpha of Image as the toolbar expands
                            alpha = state.toolbarState.progress
                        },
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(
                    List(100) { "Hello World!! $it" }
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
            }

            @OptIn(ExperimentalToolbarApi::class)
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                onClick = { }
            ) {
                Text(text = "Floating Button!")
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = enabled, onCheckedChange = { enabled = !enabled })

            Text("Enable collapse/expand", fontWeight = FontWeight.Bold)
        }
    }
}