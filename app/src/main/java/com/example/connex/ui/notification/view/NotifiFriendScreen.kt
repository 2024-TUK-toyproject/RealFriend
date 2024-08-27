package com.example.connex.ui.notification.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.theme.Body1Medium
import com.example.connex.ui.theme.Body3Regular
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray200
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray600
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.Text11ptRegular
import com.example.connex.ui.theme.White
import com.example.domain.model.notification.FriendRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotifiFriendScreen(
    friendRequests: Map<String, List<FriendRequest>>,
    acceptFriendRequest: (Long) -> Unit,
    rejectFriendRequest: (Long) -> Unit,
) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        friendRequests.forEach { (date, list) ->
            item {
                ColumnSpacer(height = 24.dp)
            }
            stickyHeader {
                Text(
                    text = date,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(start = 28.dp, bottom = 10.dp),
                    style = Body1Medium,
                    color = Gray400
                )
            }

            items(items = list, key = { it.friendRequestId }) { friendRequests ->
                NotifiFriendAskCard(
                    profile = friendRequests.profileImage,
                    name = friendRequests.name,
                    time = friendRequests.time,
                    accept = { acceptFriendRequest(friendRequests.friendRequestId) }
                ) {

                }
            }
        }

    }
}

@Composable
fun NotifiFriendAskCard(
    profile: String,
    name: String,
    time: String,
    accept: () -> Unit,
    reject: () -> Unit,
) {

    val commentStyle = TextStyle(
        fontSize = 14.sp,
//        lineHeight = 16.8.sp,
        fontWeight = FontWeight.Normal,
        color = Gray600,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 28.dp, vertical = 10.dp)
    ) {
        // TODO(Box를 기본 사진으로 바꿀 것)
        if (profile.isEmpty()) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Gray100)
                    .size(40.dp)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = profile),
                contentDescription = "image_profile",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp),
                contentScale = ContentScale.Crop
            )
        }

        // <--------------- 여기 까지
        RowSpacer(width = 12.dp)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(name)
                }
                append("님이 친구 요청을 보냈습니다.")
            }, style = commentStyle)

            ColumnSpacer(height = 4.dp)
            Text(text = time, style = Text11ptRegular, color = Gray300)
        }
        RowSpacer(width = 11.dp)
        Row(modifier = Modifier.padding(top = 9.dp)) {
            NotifiFriendIgnoreBtn { reject() }
            RowSpacer(width = 8.dp)
            NotifiFriendAskBtn { accept() }
        }


    }
}

@Composable
fun NotifiFriendAskBtn(onClick: () -> Unit) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(30.dp))
        .clickable { onClick() }
        .background(PrimaryBlue2)
        .padding(horizontal = 13.5.dp, vertical = 5.5.dp)) {
        Text(text = "수락", style = Body3Regular, color = White)
    }
}

@Composable
fun NotifiFriendIgnoreBtn(onClick: () -> Unit) {
    val shape = RoundedCornerShape(30.dp)
    Box(modifier = Modifier
        .clip(shape)
        .border(1.dp, Gray200, shape)
        .clickable { onClick() }
        .background(White)
        .padding(horizontal = 13.5.dp, vertical = 5.5.dp)) {
        Text(text = "무시", style = Body3Regular, color = Gray400)
    }
}

data class NotifiFriendAskDto(
    val profile: String,
    val name: String,
    val time: String,
)