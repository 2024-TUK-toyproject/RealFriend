package com.example.connex.ui.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcNotification
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(modifier: Modifier = Modifier) {

    val toolbarHeight = 48.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
// our offset to collapse toolbar
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
// now, let's create connection to the nested scroll system and listen to the scroll
// happening inside child LazyColumn
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)

                toolbarOffsetHeightPx.value += delta


//                Log.d(
//                    "daeyoung",
//                    "delta: $delta, newOffset: $newOffset, toolbarOffsetHeightPx: ${toolbarOffsetHeightPx.value}"
//                )
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                toolbarOffsetHeightPx.value -= available.y
                return Offset.Zero
            }
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            // attach as a parent to the nested scroll system
            .nestedScroll(nestedScrollConnection)
    ) {
        // our list with build in nested scroll support that will notify us about its scroll
        LazyColumn(contentPadding = PaddingValues(top = toolbarHeight)) {
            items(100) { index ->
                Text("I'm item $index", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
            }
        }
        TopAppBar(
            modifier = Modifier
                .height(toolbarHeight)
//                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.coerceIn(-toolbarHeightPx, 0f).roundToInt()) },
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
            title = { Text("toolbar offset is ${toolbarOffsetHeightPx.value}") }
        )
    }

//    val scrollState = rememberScrollState()
//    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { AlbumAppbar {} }
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it)
//                .verticalScroll(scrollState)
//        ) {
//            repeat(20) {
//                Text(text = "w")
//            }
//            Text(text = "${scrollState.value}")
//            repeat(30) {
//                Text(text = "w")
//            }
//        }
//        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(it)) {
//            item {
//                Box(modifier = Modifier
//                    .fillMaxWidth()
//                    .height(30.dp)
//                    .background(Color.Red))
//            }
//        }
//    }

}

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