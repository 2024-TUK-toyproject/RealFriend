package com.example.connex.ui.home.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.FriendRemoveModalBottomSheet
import com.example.connex.ui.component.HalfRoundButton
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.SearchTextField
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.home.FriendsViewModel
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcCheck
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray200
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray50
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.PrimaryBlue1
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.White

@Composable
fun FriendsRemoveScreen(
    friendsViewModel: FriendsViewModel = hiltViewModel(),
    navController: NavController,
) {
    val focusManager = LocalFocusManager.current
    val friendsRemoveUiState by friendsViewModel.friendsRemoveUiState.collectAsStateWithLifecycle()

    val isShowBottomButton by remember {
        derivedStateOf {
            friendsRemoveUiState.userList.any { it.isSelect }
        }
    }

    var isShowFriendRemoveBottomSheet by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { BackArrowAppBar(text = "친구 관리") { navController.popBackStack() } }) { paddingValue ->
        if (isShowFriendRemoveBottomSheet) {
            FriendRemoveModalBottomSheet(
                names = friendsRemoveUiState.userList.filter { it.isSelect }.map { it.friend.name },
                onClose = { isShowFriendRemoveBottomSheet = false }) {
                friendsViewModel.fetchDeleteFriend()
                isShowFriendRemoveBottomSheet = false
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                FriendsRemoveHeader(
                    count = friendsViewModel.count,
                    updateCheck = {
                        friendsViewModel.selectAllFriends(it)
                    },
                )
                SearchTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 24.dp),
                    padding = 24.dp to 10.dp,
                    backgroundColor = Color(0xFFF2F4F8),
                    placeholder = "친구의 이름이나 연락처로 검색해 보세요.",
                    text = friendsRemoveUiState.search,
                    updateText = {
                        friendsViewModel.updateFriendsRemoveSearch(it)
                        friendsViewModel.friendsRemoveUserSearch(it)
                    }
                ) {
                    friendsViewModel.friendsRemoveUserSearch(friendsRemoveUiState.search)
                    focusManager.clearFocus()
                }
                ColumnSpacer(height = 8.dp)

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = friendsRemoveUiState.userList, key = { it.friend.userId }) {
                        ContactCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    friendsViewModel.selectFriend(
                                        it.friend.userId,
                                        !it.isSelect
                                    )
                                }
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
            }
            BottomButton(isShow = isShowBottomButton, onCancel = {}) {
                isShowFriendRemoveBottomSheet = true
            }
        }

    }

}


@Composable
fun ColumnScope.FriendsRemoveHeader(
    count: Int,
    updateCheck: (Boolean) -> Unit,
) {
    var isAllChecked by rememberSaveable { mutableStateOf(false) }

    ColumnSpacer(height = 28.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "내 친구${count}명", style = Body2Medium, color = Gray500)
        AllSelectButton(isAllChecked) {
            isAllChecked = it
            updateCheck(it)
        }
    }
    ColumnSpacer(height = 16.dp)
}

@Composable
fun AllSelectButton(isCheck: Boolean, onClick: (Boolean) -> Unit) {
    val color = if (isCheck) PrimaryBlue2 else Gray400

    Row(modifier = Modifier.noRippleClickable { onClick(!isCheck) }) {
        Icon(imageVector = Icons.Default.Check, contentDescription = "allCheck", tint = color)
        RowSpacer(width = 5.dp)
        Text(text = "전체선택", fontSize = 12.sp, color = color)
    }
}

@Composable
fun ContactCardCheckBox(
    modifier: Modifier = Modifier.size(28.dp),
    isCheck: Boolean = false,
) {
    val color = if (isCheck) {
        Color.Transparent to PrimaryBlue2
    } else {
        Gray200 to Color.Transparent
    }
    Card(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = color.first),
        colors = CardDefaults.cardColors(containerColor = color.second)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isCheck) {
                Icon(
                    imageVector = IconPack.IcCheck,
                    tint = White,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun BoxScope.BottomButton(isShow: Boolean, onCancel: () -> Unit = {}, onDelete: () -> Unit = {}) {
    val style = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
    val shape = RoundedCornerShape(8.dp)
    AnimatedVisibility(
        visible = isShow,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(color = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        color = Gray100,
                        strokeWidth = 1f
                    )
                }
//                .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 17.dp)
                .padding(start = 24.dp, end = 24.dp, top = 16.dp)
        ) {
            HalfRoundButton(modifier = Modifier.weight(1f), containerColor = Gray50, contentColor = Gray500, text = "취소하기") {
                onCancel()
            }
            RowSpacer(width = 16.dp)
            HalfRoundButton(modifier = Modifier.weight(1f), containerColor = PrimaryBlue1, contentColor = White, text = "삭제하기") {
                onDelete()
            }
        }
    }
}