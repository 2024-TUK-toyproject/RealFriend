package com.example.connex.ui.notification.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray200
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Text11ptRegular

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotifiActivityScreen(modifier: Modifier = Modifier) {
    val dummy = hashMapOf<String, List<NotifiActivityDto>>(
        "오늘" to listOf(
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = listOf("", "")
            ),
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
        ),
        "어제" to listOf(
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
            NotifiActivityDto(
                profile = "",
                name = "주히",
                text = "님이 새로운 코멘트를 남겼습니다:\n여기 또 가고 싶다",
                time = "오후 4시 24분",
                image = emptyList()
            ),
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        dummy.forEach { (date, list) ->
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
                    style = Body2Medium,
                    color = Gray400
                )
            }
            items(list.size) {
                NotifiActivityCard(
                    profile = list[it].profile,
                    name = list[it].name,
                    text = list[it].text,
                    time = list[it].time,
                    image = list[it].image
                )
            }
        }

    }
}

@Composable
fun NotifiActivityCard(profile: String, name: String, text: String, time: String, image: List<String>) {

    val commentStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.8.sp,
        fontWeight = FontWeight.Normal,
        color = Gray500,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 28.dp, vertical = 10.dp)
    ) {
        // TODO(Box를 기본 사진으로 바꿀 것)
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Gray100)
                .size(40.dp)
        )
        // <--------------- 여기 까지
        RowSpacer(width = 12.dp)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(name)
                }
                append(text)
            }, style = commentStyle)
            ColumnSpacer(height = 4.dp)
            Text(text = time, style = Text11ptRegular, color = Gray300)
        }
        RowSpacer(width = 12.dp)

        // TODO(이미지 수량에 따라 분기 나눌 것)
        if (image.size >= 2) {
            NotifiActivityRotationImagesCard()
        } else {
            NotifiActivityImagesCard(image = "")
        }

    }
}

@Composable
fun NotifiActivityImagesCard(modifier: Modifier = Modifier, shadow: Dp = 0.dp, image: String) {
    val cardSize = 48.dp
    Card(
        modifier = modifier.size(cardSize),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Gray200),
        elevation = CardDefaults.elevatedCardElevation(shadow)
    ) {
        // TODO(나중에 이미지로 대체할 것)
        Box(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun NotifiActivityRotationImagesCard(modifier: Modifier = Modifier) {
    Box(modifier = Modifier) {
        NotifiActivityImagesCard(shadow = 4.dp, image = "")
        NotifiActivityImagesCard(
            modifier = Modifier
                .offset(8.dp, (-4).dp)
                .rotate(2f)
                .zIndex(-1f),
            image = ""
        )
    }
}

data class NotifiActivityDto(
    val profile: String,
    val name: String,
    val text: String,
    val time: String,
    val image: List<String>,
)

