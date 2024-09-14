package com.example.connex.ui.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.AppBarIcon
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.home.view.CreatingAlbumPlusCard
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.PrimaryBlue3
import com.example.connex.utils.Constants
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun PicturesListScreen() {
    val state = rememberCollapsingToolbarScaffoldState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
//            .padding(bottom = Constants.BottomNavigationHeight)
            .statusBarsPadding()
    ) {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = state,
            scrollStrategy = ScrollStrategy.EnterAlways,
            toolbar = {
                BackArrowAppBar2(
                    textComposable = {
                        PicturesListAppBarText(
                            text = "앨범이름",
                            userCount = 4,
                            pictureCount = 8
                        )
                    },
                    trailingIcon = Icons.Rounded.MoreVert,
                    onBack = { /*TODO*/ }) {

                }
            }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp),
            ) {
                item(span = { GridItemSpan(2) }) {
                    CreatingAlbumPlusCard(modifier = Modifier.aspectRatio(1f)) {

                    }
                }
                items(20) {
                    TestCard(modifier = Modifier.aspectRatio(1f), text = "$it") {

                    }
                }
            }
        }
    }
}

@Composable
fun TestCard(modifier: Modifier = Modifier, text: String, navigate: () -> Unit) {
    Card(
        modifier = modifier,
        onClick = { navigate() },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F3F7),
            contentColor = PrimaryBlue3
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
//            Icon(
//                imageVector = Icons.Rounded.Add,
//                contentDescription = "ic_add",
//                modifier = Modifier
//                    .size(48.dp)
//                    .align(Alignment.Center)
//            )
            Text(text = "$text")
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