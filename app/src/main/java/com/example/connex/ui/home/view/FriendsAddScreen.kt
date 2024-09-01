package com.example.connex.ui.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.connex.R
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.FriendAddModalBottomSheet
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.component.SkyBlueBox
import com.example.connex.ui.component.SmallBlueBtn
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.home.FriendsViewModel
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcConnexUsedUser
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Text16ptSemibold
import com.example.connex.utils.Constants

@Composable
fun FriendsAddScreen(
    friendsViewModel: FriendsViewModel = hiltViewModel(),
    applicationState: ApplicationState,
) {

    val friendsAddUiState = friendsViewModel.friendsAddUserList.collectAsStateWithLifecycle().value

    var isShowFriendAddBottomSheet by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        friendsViewModel.fetchReadAddedFriends {applicationState.showSnackbar("인터넷이 연결이 되어 있지 않습니다.")}
    }



    Scaffold(
        topBar = { BackArrowAppBar(text = "친구 추가") { applicationState.popBackStack() } },
        snackbarHost = { SnackbarHost(applicationState.snackbarHostState) }
    ) { innerPadding ->
//    Column(modifier = Modifier.fillMaxSize()) {
//        BackArrowAppBar(text = "친구 추가") {
//            applicationState.popBackStack()
//        }
        if (isShowFriendAddBottomSheet) {
            FriendAddModalBottomSheet {
                isShowFriendAddBottomSheet = false
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item { ColumnSpacer(height = 28.dp) }
            item {
                SkyBlueBox(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    leadingImage = {
                        Image(
                            painter = painterResource(id = R.drawable.image_mail),
                            contentDescription = "image_mail",
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center)
                        )
                    },
                    leadingImageSize = 44.dp,
                    imageAndTextPadding = 16.dp,
                    title = "connex에서 친구와 함께하려면?",
                    body = "초대 링크 복사하기",
                    enabled = true,
                    elevation = true
                ) {}
            }
            item { ColumnSpacer(height = 28.dp) }
            item { FriendAddScreenMiddleTitle { isShowFriendAddBottomSheet = true } }
            item { ColumnSpacer(height = 12.dp) }

            items(items = friendsAddUiState, key = { it.phone }) { contact ->
                if (contact.isExist) {
                    ConnexUsedContactCard(
                        modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp),
                        image = Constants.DEFAULT_PROFILE,
                        name = contact.name,
                        phone = contact.phone
                    ) {
                        SmallBlueBtn(
                            textStyle = Body3Medium,
                            text = "추가"
                        ) {
                            friendsViewModel.fetchAddFriend(contact.userId, onSuccess = {
                                applicationState.showSnackbar(
                                    "${contact.name}에게 친구 요청이 전송되었습니다."
                                )
                            }) {
                                applicationState.showSnackbar("인터넷이 연결이 되어 있지 않습니다.")
                            }
                        }
                    }
                } else {
                    ContactCard(
                        modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp),
                        image = Constants.DEFAULT_PROFILE,
                        name = contact.name,
                        phone = contact.phone
                    ) {
                        SmallBlueBtn(textStyle = Body3Medium, text = "추가") { TODO("링크 복사") }
                    }
                }
            }
        }
//    }


    }
}

@Composable
fun FriendAddScreenMiddleTitle(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "추천 친구", style = Body2Medium, color = Gray500)
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "ic_info",
            tint = Gray300,
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable { onClick() })
    }
}

@Composable
fun ConnexUsedContactCard(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    image: String,
    name: String,
    phone: String,
    iconLayout: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = name, style = Text16ptSemibold)
                RowSpacer(width = 5.dp)
                Image(
                    imageVector = IconPack.IcConnexUsedUser,
                    contentDescription = "ic_connex_useduser",
                    modifier = Modifier.size(16.dp)
                )
            }
            ColumnSpacer(height = 4.dp)
            Text(text = phone, style = Body3Regular)
        }
        RowSpacer(width = 12.dp)
        iconLayout()
    }
}
