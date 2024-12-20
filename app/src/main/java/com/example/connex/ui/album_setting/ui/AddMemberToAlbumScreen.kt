package com.example.connex.ui.album_setting.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.KeyboardDoubleArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.FriendSelectedState
import com.example.connex.ui.component.LazyRowFriends
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.home.view.ContactCard
import com.example.connex.ui.home.view.ContactCardCheckBox
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.White
import com.example.domain.entity.user.Friend
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddMemberToAlbumScreen(applicationState: ApplicationState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackArrowAppBar(text = "멤버 초대") { applicationState.popBackStack() }
        val coroutineScope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()
        val isShowScrollToTopButton by remember {
            derivedStateOf {
                lazyListState.firstVisibleItemIndex > 0 && lazyListState.lastScrolledBackward
            }
        }
        val dummyFriends by remember {
            mutableStateOf(
                listOf(
                    FriendSelectedState(
                        Friend(
                            userId = 0,
                            name = "김철수",
                            phone = "010-1234-5678",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 1,
                            name = "정대영",
                            phone = "010-1234-1683",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 2,
                            name = "김공칠",
                            phone = "010-4567-5678",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 3,
                            name = "고양이",
                            phone = "010-9054-5678",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 4,
                            name = "강아지",
                            phone = "010-1111-4444",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 5,
                            name = "장수풍뎅이",
                            phone = "010-1256-8888",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 6,
                            name = "토토로",
                            phone = "010-3586-9012",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 7,
                            name = "코롱",
                            phone = "010-3586-9012",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 8,
                            name = "뽀로로",
                            phone = "010-3586-9012",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                    FriendSelectedState(
                        Friend(
                            userId = 9,
                            name = "루피",
                            phone = "010-3586-9012",
                            profileImage = "",
                            isFriend = true
                        ), false
                    ),
                )
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
                    .padding(top = 24.dp)
            ) {
                val selectedFriends = dummyFriends.filter { it.isSelect }
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White)
                    ) {
                        CurrentSelectedMemberCountArea(current = selectedFriends.count(), max = 6)
                        ColumnSpacer(height = 10.dp)
                        LazyRowFriends(
                            modifier = Modifier.fillMaxWidth(),
                            friends = selectedFriends
                        )
                        ColumnSpacer(height = 12.dp)
                    }
                }
                item { ColumnSpacer(height = 12.dp) }
//            item {
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
                item {
                    SearchTextField(
                        modifier = Modifier
                            .height(40.dp)
                            .padding(horizontal = 24.dp),
                        padding = Pair(20.dp, 12.dp),
                        backgroundColor = Color(0xFFF2F4F8),
                        text = "",
                        placeholder = "친구의 이름이나 연락처로 검색해 보세요.",
                        updateText = {}) {}
                }
                item { ColumnSpacer(height = 12.dp) }
                items(
                    items = (dummyFriends),
                    key = { it.friend.userId }) {
                    ContactCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { it.checkOrUncheck() }
                            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
                        size = 56.dp,
                        image = it.friend.profileImage,
                        name = it.friend.name,
                        phone = it.friend.phone
                    ) {
                        ContactCardCheckBox(isCheck = it.isSelect)
                    }
                }

            }

//            if (isShowScrollToTopButton) {
                ScrollToTopButton(isShow = isShowScrollToTopButton){
                    coroutineScope.launch { lazyListState.animateScrollToItem(0) }
                }
//            }
        }

    }
}

@Composable
fun CurrentSelectedMemberCountArea(current: Int, max: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "선택된 멤버 ${current}명", style = Body2Medium, color = Gray500)
        Text(text = buildAnnotatedString {
            withStyle(SpanStyle(color = PrimaryBlue2)) {
                append("$current")
            }
            append(" / $max")
        }, style = Body2Medium, color = Gray500)
    }
}


@Composable
fun BoxScope.ScrollToTopButton(isShow: Boolean, onClick: () -> Unit, ) {
    AnimatedVisibility(
        visible = isShow,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier
            .padding(bottom = 24.dp)
            .align(Alignment.BottomCenter)
            .background(color = Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .background(brush = Brush.linearGradient(colors = listOf(White, Gray100)), alpha = 0.7f)
                .clickable { onClick() }

        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardDoubleArrowUp,
                contentDescription = "ic_scrollToTop",
                modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Center),
                tint = PrimaryBlue2.copy(alpha = 0.7f)
            )
        }
    }
}
