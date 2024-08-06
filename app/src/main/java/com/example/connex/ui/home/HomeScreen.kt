package com.example.connex.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connex.ui.component.General2Button
import com.example.connex.ui.component.ShadowBox
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.Connexlogo2
import com.example.connex.ui.svg.iconpack.IcNotification
import com.example.connex.ui.theme.FontBlack
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Heading2
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.Subtitle2

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HomeHeader(Modifier.padding(top = 32.dp, bottom = 36.dp))
        HomeBody()
        
    }
}

@Composable
fun HomeHeader(modifier: Modifier = Modifier) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                imageVector = IconPack.Connexlogo2,
                contentDescription = "app_logo",
//                modifier = Modifier.size(32.dp)
            )
            Image(
                imageVector = IconPack.IcNotification,
                contentDescription = "notification",
//                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        HomeTitle(name = "새싹")
    }
}

@Composable
fun HomeTitle(name: String) {

    val introductionStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 25.2.sp,
        color = Gray800
    )

    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 28.dp)
    ) {
        Text(text = "${name}님", style = Heading2)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "오늘 하루는 어땠는지\n친구에게 공유해 볼까요?", style = introductionStyle)
    }
}


@Composable
fun ColumnScope.HomeBody() {
    val backgroundColor = Color(0xFFF2F4F8)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(backgroundColor)
            .padding(top = 40.dp, start = 24.dp, end = 24.dp)
    ) {
        Text(text = "이건 어떠세요?", style = Subtitle2)
        Spacer(modifier = Modifier.height(16.dp))
        HomeRecommendedBoxes(Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "오늘 통화 기록", style = Subtitle2)
        Spacer(modifier = Modifier.height(16.dp))
        HomeCallLogBox(Modifier.fillMaxWidth())
    }
}

@Composable
fun HomeCallLogBox(modifier: Modifier = Modifier) {
    ShadowBox(
        modifier = modifier,
        padding = 20.dp to 21.dp,
        shape = RoundedCornerShape(12.dp)
    ) {

    }
}

@Composable
fun HomeRecommendedBoxes(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        ShadowBox(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(0.96f),
            padding = 16.dp to 24.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            HomeRecommendedContent1(modifier = Modifier.fillMaxSize(), name = "새싹", date = 2)
        }
        Spacer(modifier = Modifier.width(16.dp))
        ShadowBox(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(0.96f),
            padding = 16.dp to 24.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            HomeRecommendedContent2(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun HomeRecommendedContent1(modifier: Modifier, name: String, date: Int) {
    val titleStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        lineHeight = 14.sp,
        fontSize = 10.sp,
        color = Gray400,
        letterSpacing = 0.1.sp,
    )

    val accentStyle = SpanStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        color = PrimaryBlue2
    )

    val bodyStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.4.sp,
        fontWeight = FontWeight.SemiBold,
        color = Gray900,

        letterSpacing = 0.16.sp,
    )
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = buildAnnotatedString {
                append("${name}님과 연락한 지\n")
                withStyle(accentStyle) {
                    append("${date}일")
                }
                append(" 지났어요...")
            }, style = titleStyle)
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "next_ic", modifier = Modifier.size(20.dp), tint = Gray400)
        }
        Text(text = "${name}님께\n안부 인사를\n전해 보세요!", style = bodyStyle)
    }
}

@Composable
fun HomeRecommendedContent2(modifier: Modifier) {
    val titleStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        lineHeight = 14.sp,
        fontSize = 10.sp,
        color = Gray400,
        letterSpacing = 0.1.sp,
    )

    val bodyStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.4.sp,
        fontWeight = FontWeight.SemiBold,
        color = Gray900,
        letterSpacing = 0.16.sp,
    )
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "누구에게 안부를\n전해볼까요?", style = titleStyle)
            Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = "next_ic", modifier = Modifier.size(20.dp), tint = Gray400)
        }
        Text(text = "연락할 친구를\n" +
                "랜덤으로 추천\n" +
                "받아 보세요!", style = bodyStyle)
    }
}

@Composable
fun LongTimeContact(name: String, date: Int) {
    val text1Style = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 12.sp,
        color = Color(0xFFCACACA)
    )
    val text2Style = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 18.sp,
        color = FontBlack
    )


    Column(
        modifier = Modifier
            .padding(top = 28.dp, bottom = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            style = text1Style,
            text = buildAnnotatedString {
                append("${name}님과 연락한 지 ")
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append("${date}일")
                }
                append(" 지났어요...")
            })
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${name}님과 안부 인사 어떠세요?",
            style = text2Style,
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        General2Button(
            modifier = Modifier.padding(horizontal = 14.dp),
            text = "친구 목록 보기",
            enabled = true
        ) {

        }
    }
}