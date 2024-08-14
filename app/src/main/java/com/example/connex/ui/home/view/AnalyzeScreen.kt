package com.example.connex.ui.home.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connex.ui.theme.ConnexTheme

@Composable
fun AnalyzeScreen(modifier: Modifier = Modifier) {

}

private var isHeaderHide = false

internal class BackdropScrollConnection(
    private val scrollState: ScrollState,
) : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val dy = available.y

        return when {
            /**
             * 헤더가 보이지 않는 상태일 경우, onPreScroll에서 Offset.Zero를 반환하여 부모가 스크롤을 받지 않도록 한다.
             */
            isHeaderHide -> {
                Offset.Zero
            }

            /**
             * 터치를 아래에서 위로, 즉 화면을 위로 올릴 때,
             * 부모가 스크롤 이벤트를 우선적으로 받는다.
             */
            dy < 0 -> {
                scrollState.dispatchRawDelta(dy * -1)
                Offset(0f, dy)
            }

            /**
             * 터치를 위에서 아래로, 즉 화면을 아래로 내릴 때,
             * NestedScrollConnection을 통해 부모가 스크롤 이벤트를 받지 않도록 한다.
             */
            else -> {
                Offset.Zero
            }
        }
    }
}

@Composable
fun CollapsingToolbarScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    header: @Composable () -> Unit = {},
    stickyHeader: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable () -> Unit,
) {
    val scrollState = rememberScrollState()

    val nestedScrollConnection = remember {
        BackdropScrollConnection(
            scrollState = scrollState,
        )
    }


    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            var globalHeight by remember { mutableIntStateOf(0) }
//            globalHeight.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .onSizeChanged { size ->
                        globalHeight = size.height
                    }
                    .nestedScroll(
                        connection = nestedScrollConnection,
                    )
                    .verticalScroll(scrollState),
            ) {
                HeaderSection(
                    header = header,
                    onHide = { isHide ->
                        isHeaderHide = isHide
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(globalHeight.dp)
//                        .height(globalHeight.pxToDp().dp)
                ) {
                    stickyHeader()
                    content()
                }
            }
        }
    }

}

@Composable
private fun HeaderSection(
    header: @Composable () -> Unit,
    onHide: (Boolean) -> Unit,
) {
    var contentHeight by remember { mutableStateOf(0) }
    var visiblePercentage by remember { mutableStateOf(1f) }

    /**
     * 헤더가 보이는 비율이 변경되면, onHide를 호출하여 헤더가 보이는지 여부를 전달한다.
     */
    LaunchedEffect(visiblePercentage) {
        if (visiblePercentage <= 0f) {
            onHide(true)
        } else {
            onHide(false)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .onGloballyPositioned { layoutCoordinates ->
                /**
                 * 헤더가 보이는 비율을 계산한다.
                 */
                visiblePercentage = layoutCoordinates.boundsInRoot().height / contentHeight
            }
            .onSizeChanged {
                contentHeight = it.height
            }
            .alpha(visiblePercentage)
    ) {
        header()
    }
}

//@OptIn(ExperimentalFoundationApi::class)
@Preview(
    showBackground = true,
    showSystemUi = false,
)
@Composable
internal fun CollapsingToolbarScaffoldPreview() {
    ConnexTheme {
        CollapsingToolbarScaffold(
            topBar = {
                Text(text = "TopBar")
            },
            header = {
                repeat(10) {
                    Text(text = "Header")
                }
            },
            stickyHeader = {
                Text(text = "Sticky Header")
            },
        ) {
            HorizontalPager(
//                pageCount = 5,
                state = rememberPagerState{5},
                contentPadding = PaddingValues(start = 20.dp, end = 120.dp),
                pageSpacing = 8.dp
            ) { index ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(100) { itemNumber ->
                        Text(text = "page: $index, item: $itemNumber")
                    }
                }
            }
        }
    }

}