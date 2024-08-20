package com.example.connex.ui.home.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.connex.ui.component.General2Button
import com.example.connex.ui.component.General3Button
import com.example.connex.ui.component.ShadowBox
import com.example.connex.ui.component.util.noRippleClickable
import com.example.connex.ui.home.HomeViewModel
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.Connexlogo2
import com.example.connex.ui.svg.iconpack.IcNotification
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.Gray900
import com.example.connex.ui.theme.Head2Semibold
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.Subtitle2
import com.example.connex.utils.Constants
import com.example.connex.utils.Constants.NOTIFICATION_ROUTE

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        homeViewModel.fetchReadMostCallUsers()
    }

    Column(
        modifier = Modifier
//            .navigationBarsPadding()
            .padding(bottom = Constants.BottomNavigationHeight)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HomeHeader(Modifier.padding(top = 32.dp, bottom = 36.dp)) {
            navController.navigate(NOTIFICATION_ROUTE)
        }
        HomeBody()

    }
}

@Composable
fun HomeHeader(modifier: Modifier = Modifier, navigate: () -> Unit) {
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
                modifier = Modifier.noRippleClickable { navigate() }
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
        Text(text = "${name}님", style = Head2Semibold)
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
//            .weight(1f)
            .background(backgroundColor)
            .padding(top = 40.dp, start = 24.dp, end = 24.dp, bottom = 30.dp)
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
        Column(modifier = Modifier.fillMaxSize()) {
            HomeCallLogHeader(date = "07일 22일 20시", duration = "45", difference = "10")
            HomeCallLogBody(modifier = Modifier.fillMaxWidth())
            General3Button(modifier = Modifier.height(47.dp), text = "상세 분석 보기") {

            }
        }
    }
}

@Composable
fun ColumnScope.HomeCallLogHeader(date: String, duration: String, difference: String) {
    val dateStyle = TextStyle(
        fontSize = 10.sp,
        color = Gray300,
        lineHeight = 12.sp,
        letterSpacing = 0.1.sp,
    )

    val titleStyle = TextStyle(
        fontSize = 10.sp,
        color = Gray300,
        lineHeight = 12.sp,
        letterSpacing = 0.1.sp,
    )

    val subTitleStyle = TextStyle(
        fontSize = 10.sp,
        color = Gray300,
        lineHeight = 12.sp,
        letterSpacing = 0.1.sp,
    )
    Text(text = "${date} 기준", style = dateStyle)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = buildAnnotatedString {
        withStyle(SpanStyle()) {
            append("${duration}분 ")
        }
        append("통화했어요!")
    })
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = buildAnnotatedString {
        append("어제보다 ")
        withStyle(SpanStyle()) {
            append("${difference}분 ")
        }
        append("더 통화했어요.")
    })
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ColumnScope.HomeCallLogBody(modifier: Modifier) {
    // 20분 39초
    var time1 = 20 * 60 + 39
    // 8분 21초
    var time2 = 8 * 60 + 21
    // 4분
    var time3 = 4 * 60

    var allTime = time1 + time2 + time3
    var time1Rate = time1 / allTime.toFloat()
    var time2Rate = time2 / allTime.toFloat()

    val callLogUsers = listOf(
        CallLogTop3User(
            color = Color(0xFF5074F2),
            name = "지땡",
            time = "20분 39초"
        ),
        CallLogTop3User(
            color = Color(0xFFA1B0FF),
            name = "서빵",
            time = "8분 21초"
        ),
        CallLogTop3User(
            color = Color(0xFFACCDFF),
            name = "주히",
            time = "4분"
        )
    )


    Canvas(modifier = modifier.padding(horizontal = 6.dp)) {
        drawLine(
            color = Color(0xFFACCDFF),
            start = Offset.Zero,
            end = Offset(this.size.width, 0f),
            strokeWidth = 40f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color(0xFFA1B0FF),
            start = Offset(this.size.width * time1Rate, 0f),
            end = Offset(this.size.width * time1Rate + this.size.width * time2Rate, 0f),
            strokeWidth = 40f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color(0xFF5074F2),
            start = Offset.Zero,
            end = Offset(this.size.width * time1Rate, 0f),
            strokeWidth = 40f,
            cap = StrokeCap.Round
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    callLogUsers.forEachIndexed { index, callLogTop3User ->
        HomeCallLogBodyUser(
            color = callLogTop3User.color,
            name = callLogTop3User.name,
            time = callLogTop3User.time
        )
        if (index != callLogUsers.lastIndex) {
            Spacer(modifier = Modifier.height(16.dp))
        } else {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}


@Composable
fun ColumnScope.HomeCallLogBodyUser(color: Color, name: String, time: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier
            .size(14.dp)
            .clip(CircleShape)
            .background(color))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = name)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = time)
        }
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = buildAnnotatedString {
                append("${name}님과 연락한 지\n")
                withStyle(accentStyle) {
                    append("${date}일")
                }
                append(" 지났어요...")
            }, style = titleStyle)
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "next_ic",
                modifier = Modifier.size(20.dp),
                tint = Gray400
            )
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "누구에게 안부를\n전해볼까요?", style = titleStyle)
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "next_ic",
                modifier = Modifier.size(20.dp),
                tint = Gray400
            )
        }
        Text(
            text = "연락할 친구를\n" +
                    "랜덤으로 추천\n" +
                    "받아 보세요!", style = bodyStyle
        )
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
        color = Gray900
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

data class CallLogTop3User(
    val color: Color,
    val name: String,
    val time: String
)