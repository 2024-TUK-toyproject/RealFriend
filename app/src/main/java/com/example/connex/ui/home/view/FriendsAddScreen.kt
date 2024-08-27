package com.example.connex.ui.home.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.SkyBlueBox
import com.example.connex.ui.component.SmallBlueBtn
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.home.FriendsViewModel
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.ImageMail
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Gray500
import com.example.connex.utils.Constants

@Composable
fun FriendsAddScreen(friendsViewModel: FriendsViewModel = hiltViewModel(), applicationState: ApplicationState) {

    val friendsAddUiState = friendsViewModel.friendsAddUserList.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        friendsViewModel.readAddedFriends()
    }

    Scaffold(topBar = {
        BackArrowAppBar(text = "친구 추가") {

        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item { ColumnSpacer(height = 28.dp) }
            item {
                SkyBlueBox(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    leadingImage = IconPack.ImageMail,
                    leadingImageSize = 44.dp,
                    imageAndTextPadding = 16.dp,
                    title = "connex에서 친구와 함께하려면?",
                    body = "초대 링크 복사하기",
                    enabled = true,
                    elevation = true
                ) {}
            }
            item { ColumnSpacer(height = 37.dp) }
            item { FriendAddScreenMiddleTitle() }

            items(items = friendsAddUiState, key = { it.userId }) {contact ->
                ContactCard(
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp),
                    image = Constants.DEFAULT_PROFILE,
                    name = if(contact.isAppUsed) "${contact.name} !!" else contact.name,
                    phone = contact.phone
                ) {
                    SmallBlueBtn(onClick = { }, textStyle = Body3Medium, text = "추가")
                }
            }
        }
    }
}

@Composable
fun FriendAddScreenMiddleTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(text = "내 연락처..", style = Body2Medium, color = Gray500)
    }
}