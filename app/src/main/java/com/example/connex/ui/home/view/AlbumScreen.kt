package com.example.connex.ui.home.view


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcNotification
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.connex.ui.theme.White
import com.example.connex.utils.Constants
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.roundToInt

data class TempleAlbumData(
    val id: Long = 0,
    val image: String = " ",
    val album: String = "",
)

//
////private var isHeaderHide = false
////
var AlbumHeaderLayout = "AlbumHeaderLayout"
var AlbumSearchLayout = "AlbumSearchLayout"
var AlbumBodyLayout = "AlbumBodyLayout"

val HeaderGradientHeight = 24.dp

//
//internal class BackdropScrollConnection(
//    private val scrollState: LazyGridState,
//) : NestedScrollConnection {
//    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//        val dy = available.y
//        return Offset.Zero
//        return when {
////            /**
////             * 헤더가 보이지 않는 상태일 경우, onPreScroll에서 Offset.Zero를 반환하여 부모가 스크롤을 받지 않도록 한다.
////             */
////            isHeaderHide -> {
////                Offset.Zero
////            }
////
////            /**
////             * 터치를 아래에서 위로, 즉 화면을 위로 올릴 때,
////             * 부모가 스크롤 이벤트를 우선적으로 받는다.
////             */
//            dy < 0 -> {
//                scrollState.dispatchRawDelta(dy * -1)
//                Offset(0f, dy)
//            }
////
////            /**
////             * 터치를 위에서 아래로, 즉 화면을 아래로 내릴 때,
////             * NestedScrollConnection을 통해 부모가 스크롤 이벤트를 받지 않도록 한다.
////             */
//            else -> {
//                Offset.Zero
//            }
//        }
//    }
//}

@OptIn(
    ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AlbumScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberLazyGridState()
    var headerHeight by remember { mutableIntStateOf(0) }
    var searchHeight by remember { mutableIntStateOf(0) }


    val dummyList = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    var count = 110
    Scaffold(topBar = { AlbumAppbar {} }) { paddingValues ->
        LazyColumn(modifier = Modifier
            .padding(paddingValues)
            .onGloballyPositioned { }) {
            item { ColumnSpacer(height = 28.dp) }
            item { AlbumCreateBox() }
            item { ColumnSpacer(height = 24.dp) }
            item {
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
            }
            item { ColumnSpacer(height = 24.dp) }
            stickyHeader { AlbumSearchSection(category = "즐겨찾기 순", search = "") {} }
//            itemsIndexed(dummyList) { index, item ->
//                FlowRow {
//
//                }
//            }
            item {
                FlowRow(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    repeat(11) {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(160.dp)
                                .background(Color.Red)
                        )
                    }
                }
            }

        }
//            item(count) {
//                FlowRow(
//                    maxItemsInEachRow = 3,
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(20.dp),
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .width(100.dp)
//                            .padding(end = 5.dp, bottom = 5.dp)
//                            .height(160.dp)
//                            .background(Color.Red)
//                    )
//                }
//            }


    }
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            contentPadding = PaddingValues(
//                top = headerHeight,
//                bottom = HeaderGradientHeight,
//                start = HeaderGradientHeight,
//                end = HeaderGradientHeight
//            ),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalArrangement = Arrangement.spacedBy(20.dp),
//            modifier = Modifier.layoutId(AlbumBodyLayout),
//            state = scrollState
//        ) {
//            items(110) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(160.dp)
//                        .background(Color.Red)
//                )
//            }
//        }
}
//    FavoriteContentLayout(
//        firstVisibleItemIndex = {
//            scrollState.firstVisibleItemIndex
//        },
//        firstVisibleItemScrollOffset = {
//            scrollState.firstVisibleItemScrollOffset
//        },
//        scrollState = scrollState
//    ) {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            contentPadding = PaddingValues(bottom = HeaderGradientHeight, start = HeaderGradientHeight, end = HeaderGradientHeight),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalArrangement = Arrangement.spacedBy(20.dp),
//            modifier = Modifier.layoutId(AlbumBodyLayout),
//            state = scrollState
//        ) {
//            items(110) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(160.dp)
//                        .background(Color.Red)
//                )
//            }
//        }
//        AlbumSearchSection(category = "즐겨찾기 순", search = "") {}
////        Spacer(modifier = Modifier
////            .fillMaxWidth()
////            .height(HeaderGradientHeight))
//////        FavoriteHeaderContent()
//        AlbumHeaderSection()
//    }


@Composable
fun AlbumAppbar(modifier: Modifier = Modifier, onClick: () -> Unit) {
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
            .padding(vertical = 16.dp, horizontal = 24.dp),
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

//
//@Composable
//fun AlbumLayout(
//    firstVisibleItemScrollOffset: () -> Int,
//    firstVisibleItemIndex: () -> Int,
////    scrollState: LazyGridState,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit,
//) {
//
//    Layout(modifier = modifier, content = content) { measurables, constraints ->
//        val fixedHeight = 40.dp.toPx().roundToInt()
//
//        val AlbumHeaderPlaceable =
//            measurables.first { it.layoutId == AlbumHeaderLayout }.measure(constraints)
//        val AlbumBodyPlaceable =
//            measurables.first { it.layoutId == AlbumBodyLayout }.measure(constraints)
//
//        val scrollIndex = firstVisibleItemIndex()
//        val scrollOffset = firstVisibleItemScrollOffset()
//        val dynamicHeight = AlbumHeaderPlaceable.height - fixedHeight
//        val offset = if (scrollIndex == 0) {
//            -min(scrollOffset, dynamicHeight)
//        } else {
//            -dynamicHeight
//        }
//
//        layout(width = constraints.maxWidth, height = constraints.maxHeight) {
//            AlbumBodyPlaceable.placeRelative(x = 0, y = AlbumHeaderPlaceable.height + offset)
//            AlbumHeaderPlaceable.placeRelative(x = 0, y = offset)
//        }
//    }
//}
//
@Composable
fun AlbumHeaderSection(onSizeChanged: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .layoutId(AlbumHeaderLayout)
            .padding(horizontal = 24.dp)
    ) {
        ColumnSpacer(height = 28.dp)
        AlbumCreateBox()
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
        AlbumSearchSection(
            category = "즐겨찾기 순",
            search = ""
        ) {}
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
        Text(text = "새로 올라온 사진 모아보기 📷", style = Body1Semibold, color = Gray900, modifier = Modifier.padding(horizontal = 24.dp))
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
            .layoutId(AlbumSearchLayout)
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

//@Composable
//fun AlbumListSection(scrollState: LazyGridState) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        contentPadding = PaddingValues(horizontal = 24.dp),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        verticalArrangement = Arrangement.spacedBy(20.dp),
//        modifier = Modifier.layoutId(AlbumBodyLayout),
//        state = scrollState
//    ) {
//        items(7) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(160.dp)
//                    .background(Color.Red)
//            )
//        }
//    }
//}


//@Composable
//private fun FavoriteHeaderContent(modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier.layoutId(AlbumHeaderLayout)
//    ) {
//        // 1. TextHeader
//        repeat(10) {
//            Text(text = "뭉먼엄넝ㅂㅈ댜ㅏ암ㄴㅇ", modifier = Modifier.wrapContentHeight())
//        }
//        // 2. SearchTextField
//        Box(modifier = Modifier
//            .fillMaxWidth()
//            .height(HeaderGradientHeight))
//    }
//}

@Composable
private fun FavoriteContentLayout(
    firstVisibleItemScrollOffset: () -> Int,
    firstVisibleItemIndex: () -> Int,
    scrollState: LazyGridState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var scrollableHeight by remember { mutableIntStateOf(0) }
    var appbarOffsetHeightPx by remember { mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // 여기에 있던 coerceIn을 SearchAppBar로 이동
                appbarOffsetHeightPx += available.y
//                Log.d("daeyoung", "appbarOffsetHeightPx: ${appbarOffsetHeightPx}")
//                Log.d("daeyoung", "scrollableHeight: ${scrollableHeight}")
//                if (appbarOffsetHeightPx.absoluteValue < scrollableHeight) {
//                    coroutineScope.launch { scrollState.scrollBy(0f) }
//                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                appbarOffsetHeightPx -= available.y
                return Offset.Zero
            }
        }
    }

    Layout(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
            .offset {
                IntOffset(
                    x = 0, y = appbarOffsetHeightPx
                        .coerceIn(-scrollableHeight.toFloat(), 0f) // 여기로 이동
                        .roundToInt()
                )
            },
        content = content
    ) { measurables, constraints ->
        val gridPlaceable =
            measurables.first { it.layoutId == AlbumBodyLayout }.measure(constraints)
        val headerPlaceable =
            measurables.first { it.layoutId == AlbumHeaderLayout }.measure(constraints)
        val searchPlaceable =
            measurables.first { it.layoutId == AlbumSearchLayout }.measure(constraints)
//        val fixedHeight = 40.dp.toPx().roundToInt()

        val scrollIndex = firstVisibleItemIndex()
        val scrollOffset = firstVisibleItemScrollOffset()
        scrollableHeight = headerPlaceable.height
        val dynamicHeight = headerPlaceable.height
        val offset = if (scrollIndex == 0) {
            -min(scrollOffset, dynamicHeight)
        } else {
            -dynamicHeight
        }

        Log.d("daeyoung", "scrollIndex: ${scrollIndex}")


        Log.d(
            "daeyoung",
            "scrollOffset: ${scrollOffset}\nheaderPlaceable.height: ${headerPlaceable.height}\nsearchPlaceable: ${searchPlaceable.height}\ngridPlaceable: ${gridPlaceable.height}"
        )
        Log.d("daeyoung", "offset: ${offset}")

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
//            gridPlaceable.placeRelative(0, headerPlaceable.height + searchPlaceable.height + offset)
//            headerPlaceable.placeRelative(0, offset)
//            searchPlaceable.placeRelative(0, headerPlaceable.height + offset)
            gridPlaceable.placeRelative(0, headerPlaceable.height + searchPlaceable.height)
            headerPlaceable.placeRelative(0, 0)
            searchPlaceable.placeRelative(0, headerPlaceable.height)
        }
    }
}