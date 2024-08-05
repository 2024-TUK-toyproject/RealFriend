package com.example.connex.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.connex.ui.theme.PrimaryBlue1

@Composable
fun HomeScreen() {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(64.dp - statusBarPadding))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
//                imageVector = rememberConnexLogo2(),
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
        TitleText(name = "새싹", pictureCount = 2)
        Spacer(modifier = Modifier.height(40.dp))
        ShadowBox(modifier = Modifier.padding(horizontal = 24.dp)) {
            LongTimeContact(name = "영이", date = 12)
        }
    }
}

@Composable
fun TitleText(name: String, pictureCount: Int) {
    val nameStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = FontBlack
    )
    val introductionStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 18.sp,
        color = FontBlack
    )
    val accentStyle = SpanStyle(
        fontWeight = FontWeight.SemiBold,
        color = PrimaryBlue1
    )


    Column(
        Modifier
            .fillMaxWidth()
            .padding(start = 28.dp)
    ) {
        Text(text = "${name}님", style = nameStyle)
        Text(text = buildAnnotatedString {
            append("새로운 ")
            withStyle(accentStyle) {
                append("${pictureCount}개")
            }
            append("의 사진이 공유됐어요!")
        }, style = introductionStyle)

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