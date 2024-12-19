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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.component.ShareAlbumModalBottomSheet
import com.example.connex.ui.component.TempleAlbumData
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.home.FriendsViewModel
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.ConnexLogoGreen
import com.example.connex.ui.svg.iconpack.IcAddUser
import com.example.connex.ui.svg.iconpack.IcAlbumOff
import com.example.connex.ui.svg.iconpack.IcAlbumOn
import com.example.connex.ui.svg.iconpack.IcCall
import com.example.connex.ui.svg.iconpack.IcSettingList
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray200
import com.example.connex.ui.theme.Gray600
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.connex.ui.theme.Text16ptSemibold
import com.example.connex.ui.theme.White
import com.example.connex.utils.Constants

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FriendsScreen(
    friendsViewModel: FriendsViewModel = hiltViewModel(),
    applicationState: ApplicationState,
) {
    val dummys = listOf(
        TempleAlbumData(
            "https://shpbucket.s3.amazonaws.com/profile/default_profile/default.png",
            "앨범 1"
        ),
        TempleAlbumData(
            "https://shpbucket.s3.amazonaws.com/profile/default_profile/default.png",
            "앨범 1"
        ),
        TempleAlbumData(
            "https://shpbucket.s3.amazonaws.com/profile/default_profile/default.png",
            "앨범 1"
        )
    )


    val focusManager = LocalFocusManager.current
    val friendsUiState by friendsViewModel.friendsUiState.collectAsStateWithLifecycle()


    val scrollState = rememberLazyListState()
    var subTitleHeight by remember {
        mutableIntStateOf(0)
    }

    val appbarAlphaPercent by remember {
        derivedStateOf {
            if (subTitleHeight != 0 && scrollState.firstVisibleItemIndex >= 2) {
                1f
            } else if (subTitleHeight != 0 && scrollState.firstVisibleItemIndex == 1 && scrollState.firstVisibleItemScrollOffset > 0) {
                scrollState.firstVisibleItemScrollOffset / subTitleHeight.toFloat()
            } else {
                0f
            }
        }
    }

    var isShowShareAlbumBottomSheet by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        Log.d("FriendsScreen", "LaunchedEffect")
        friendsViewModel.fetchReadAllFriends { applicationState.showSnackbar("인터넷이 연결이 되어 있지 않습니다.") }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().padding(bottom = Constants.BottomNavigationHeight).statusBarsPadding(),
        topBar = {
            FriendsScreenAppBar(
                count = friendsUiState.userList.size,
                alpha = appbarAlphaPercent,
                onNavigate1 = { applicationState.navigate(Constants.FRIEND_ADD_ROUTE) }) {
                applicationState.navigate(Constants.FRIEND_REMOVE_ROUTE)
            }
        }) { paddingValue ->
        if (isShowShareAlbumBottomSheet) {
            ShareAlbumModalBottomSheet(
                albums = dummys,
                onClose = { isShowShareAlbumBottomSheet = false }) {

            }
        }
        Surface(modifier = Modifier.padding(paddingValue)) {
            LazyColumn(
                modifier = Modifier
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
                        text = "내 친구 ${friendsUiState.userList.size}명",
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
                        updateSearch = {
                            friendsViewModel.updateFriendsSearch(it)
                            friendsViewModel.friendsUserSearch(it)
                        },
                        search = friendsUiState.search,
                        onSearch = {
                            friendsViewModel.friendsUserSearch(friendsUiState.search)
                            focusManager.clearFocus()
                        }
                    )
                }
                items(items = friendsUiState.userList, key = { it.userId }) { item ->
                    ContactCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 20.dp),
                        image = item.profileImage,
                        name = item.name,
                        phone = item.phone
                    ) {
                        Row {
                            ContactCardIconButton(icon = IconPack.IcCall) {}
                            RowSpacer(width = 12.dp)
                            val iconAndEnabled = if (item.isFriend) {
                                IconPack.IcAlbumOn to true
                            } else {
                                IconPack.IcAlbumOff to false
                            }
                            ContactCardIconButton(
                                icon = iconAndEnabled.first,
                                enabled = iconAndEnabled.second
                            ) { isShowShareAlbumBottomSheet = true }
                        }
                    }
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
fun FriendsScreenAppBar(
    modifier: Modifier = Modifier,
    count: Int,
    alpha: Float,
    onNavigate1: () -> Unit,
    onNavigate2: () -> Unit,
) {

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
            Text(text = "친구", style = Head2Semibold)
            Text(
                text = " ${count}명",
                modifier = Modifier.alpha(alpha),
                style = Head2Semibold
            )
        }

        Row {
            Icon(
                imageVector = IconPack.IcAddUser,
                contentDescription = "addUser",
                modifier = Modifier.noRippleClickable { onNavigate1() })
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = IconPack.IcSettingList,
                contentDescription = "notification",
                modifier = Modifier.noRippleClickable { onNavigate2() })
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
    Box(
        modifier = modifier
            .background(Color.White)
            .padding(top = 16.dp, bottom = 4.dp)
    ) {
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


@Composable
fun ContactCard(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    backgroundColor: Color = White,
    image: String,
    name: String,
    phone: String,
    iconLayout: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.background(backgroundColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(size),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Gray100)
        ) {
            Box(Modifier.fillMaxSize()) {
                Image(
                    painter = rememberAsyncImagePainter(model = image),
                    contentDescription = "default_profile",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
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
        iconLayout()
    }
}

@Composable
fun ContactCardIconButton(
    modifier: Modifier = Modifier.size(36.dp),
    icon: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = { onClick() },
        enabled = enabled,
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

@Composable
fun BottomSheetAlbumCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    image: String,
    name: String,
) {
    Box(modifier = modifier.noRippleClickable { onClick() }, contentAlignment = Alignment.Center) {
        Column {

            Card(
                modifier = Modifier
                    .height(112.dp)
                    .width(96.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Gray100)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = image),
                    contentDescription = "album_image",
                    modifier = Modifier.fillMaxSize()
                )
            }
            ColumnSpacer(height = 7.dp)
            Text(
                text = name,
                style = Body2Medium,
                color = Gray600,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
    }
}
