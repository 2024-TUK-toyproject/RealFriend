package com.example.connex.ui.home.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.ColumnWhiteSpacer
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.ConnexLogoGreen
import com.example.connex.ui.svg.iconpack.ConnexLogoWhite
import com.example.connex.ui.svg.iconpack.IcAddUser
import com.example.connex.ui.svg.iconpack.IcAlbumOff
import com.example.connex.ui.svg.iconpack.IcCall
import com.example.connex.ui.svg.iconpack.IcMenudot
import com.example.connex.ui.svg.iconpack.IcSettingList
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray200
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Heading2
import com.example.connex.ui.theme.Text16ptSemibold
import com.example.connex.utils.Constants.BottomNavigationHeight

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsScreen(modifier: Modifier = Modifier) {


    var search by remember {
        mutableStateOf("")
    }

    val scrollState = rememberLazyListState()
    var subTitleHeight by remember {
        mutableIntStateOf(0)
    }

    val appbarAlphaPercent by remember {
        derivedStateOf {
//            Log.d(
//                "daeyoung",
//                "subTitleHeight: $subTitleHeight\nscrollState.firstVisibleItemIndex: ${scrollState.firstVisibleItemIndex}\nscrollState.firstVisibleItemScrollOffset: ${scrollState.firstVisibleItemScrollOffset}"
//            )
            if (subTitleHeight != 0 && scrollState.firstVisibleItemIndex >= 2) {
                1f
            } else if (subTitleHeight != 0 && scrollState.firstVisibleItemIndex == 1 && scrollState.firstVisibleItemScrollOffset > 0) {
                scrollState.firstVisibleItemScrollOffset / subTitleHeight.toFloat()
            } else {
                0f
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { FriendsScreenAppBar(alpha = appbarAlphaPercent) }) {
        Surface(modifier = Modifier.padding(it)) {
//            FriendsLayout()
            LazyColumn(
                modifier = Modifier
                    .padding(bottom = BottomNavigationHeight)
                    .fillMaxSize(),
                state = scrollState
            ) {
                item {
                    FriendsScreenHeader(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth()
//                .aspectRatio(312f / 91f)
                    )
                }
                item {
                    Text(
//                        text = "내 친구 ${count}명",
                        text = "내 친구 8명",
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .onSizeChanged {
                                subTitleHeight = it.height
                            },
                        style = Body2Medium
                    )
                }
                stickyHeader {
                    FriendsScreenStickyHeader(
//                        modifier = Modifier.layoutId(StickyHeaderLayoutID),
                        updateSearch = { search = it },
                        search = search,
                        onSearch = {}
                    )
                }
                items(10, key = { it }) { itemNumber ->
                    ContactCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 20.dp),
                        name = "김민수",
                        phone = "010-1234-5678"
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 0.5.dp,
                        color = Gray100
                    )
                }
            }
        }
    }
}

@Composable
fun FriendsScreenAppBar(modifier: Modifier = Modifier, alpha: Float) {

    Row(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    color = Gray100,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 0.5.dp.toPx()
                )
            }
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text(text = "친구", style = Heading2)
            Text(
                text = " 8명",
                modifier = Modifier.alpha(alpha),
                style = Heading2
            )
        }

        Row {
            Icon(imageVector = IconPack.IcAddUser, contentDescription = "addUser")
            Spacer(modifier = Modifier.width(20.dp))
            Icon(imageVector = IconPack.IcSettingList, contentDescription = "notification")
        }
    }

}

@Composable
fun FriendsScreenHeader(modifier: Modifier = Modifier) {
    ColumnSpacer(32.dp)
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDC3), contentColor = Gray900)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, bottom = 24.dp, start = 24.dp, end = 23.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
            ) {
                Text(text = "누구에게 안부를 전해볼까요?", style = Body1Semibold)
                ColumnSpacer(height = 4.dp)
                Text(text = "연락할 친구를 랜덤으로 추천받아 보세요!", style = Body3Medium)
            }
            Image(imageVector = IconPack.ConnexLogoGreen, contentDescription = null)
        }

    }
    ColumnSpacer(28.dp)

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FriendsScreenStickyHeader(
    modifier: Modifier = Modifier,
    updateSearch: (String) -> Unit,
    search: String,
    onSearch: () -> Unit,
) {
//    ColumnWhiteSpacer(height = 16.dp)
    Box(modifier = modifier.background(Color.White).padding(top = 16.dp, bottom = 4.dp)) {
        SearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 24.dp),
            padding = 24.dp to 10.dp,
            backgroundColor = Color(0xFFF2F4F8),
            text = search,
            placeholder = "친구의 이름이나 연락처로 검색해 보세요.",
            updateText = updateSearch
        ) {
            onSearch()
        }
    }
//    ColumnWhiteSpacer(height = 4.dp)


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ColumnScope.FriendsScreenBody(
    modifier: Modifier = Modifier,
    count: Int,
    search: String,
    updateSearch: (String) -> Unit,
    onSearch: () -> Unit,
) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {

    }
}

@Composable
fun ContactCard(modifier: Modifier = Modifier, name: String, phone: String) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Gray100)
        ) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    imageVector = IconPack.ConnexLogoWhite,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        RowSpacer(width = 22.dp)
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(text = name, style = Text16ptSemibold)
            ColumnSpacer(height = 4.dp)
            Text(text = phone, style = Body3Regular)
        }
        RowSpacer(width = 12.dp)
        Row {
            ContactCardIconButton(icon = IconPack.IcCall) {}
            RowSpacer(width = 10.dp)
            ContactCardIconButton(icon = IconPack.IcAlbumOff) {}
            RowSpacer(width = 10.dp)
            ContactCardIconButton(icon = IconPack.IcMenudot) {}
        }
    }
}

@Composable
fun ContactCardIconButton(
    modifier: Modifier = Modifier.size(32.dp),
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = { onClick() },
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = Gray200),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}