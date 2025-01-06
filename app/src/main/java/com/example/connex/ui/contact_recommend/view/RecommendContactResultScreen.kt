package com.example.connex.ui.contact_recommend.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.HorizontalGrayDivider
import com.example.connex.ui.component.ProfileCard
import com.example.connex.ui.component.RowSpacer
import com.example.connex.ui.svg.IconPack
import com.example.connex.ui.svg.iconpack.IcCall
import com.example.connex.ui.svg.iconpack.IcLoudspeaker
import com.example.connex.ui.svg.iconpack.IcSmileface
import com.example.connex.ui.theme.Body1Medium
import com.example.connex.ui.theme.Body1SemiBold
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Gray150
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.Heading1
import com.example.connex.ui.theme.PrimaryBlue2
import com.example.connex.ui.theme.PrimaryBlue3
import java.time.LocalDate

@Composable
fun RecommendContactResultScreen() {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val num = Uri.parse("tel:010-1234-1234")
    val intent = Intent(Intent.ACTION_CALL, num)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColumnSpacer(height = 32.dp)
            Text(text = "등록된 친구를 기반으로 추천드렸어요", style = Heading1)
            ColumnSpacer(height = 24.dp)
            CallUsageArea(
//                modifier = Modifier.weight(1f),
                image = "",
                name = "누구게",
                phone = "010-1234-5678",
                month = 12,
                percent = 13.1f
            )
            CallLogCountArea(month = 12, send = 1, receive = 2, name = "누구게")

            ColumnSpacer(height = 16.dp)
            HorizontalGrayDivider()
            ColumnSpacer(height = 16.dp)
            Text(text = "12월 통화 기록", style = Body1SemiBold.copy(Gray800), modifier = Modifier.fillMaxWidth())
            ColumnSpacer(height = 24.dp)
            val data = listOf(1, 2, 3, 5, 1, 2, 3, 5, 12, 0, 0, 0, 12, 3, 1, 1, 2, 1, 5)
            CallLogLineGraph(modifier = Modifier.fillMaxWidth(), month = 12, data = data)
        }

        Column(
//            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ColumnSpacer(height = 8.dp)
            GeneralButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .height(51.dp), text = "연락하러 가기"
            ) {
                context.startActivity(intent)
            }
            ColumnSpacer(height = 8.dp)
            Text(
                text = "다른 사람과 연락하고 싶어요",
                textDecoration = TextDecoration.Underline,
                style = Body3Medium.copy(Gray400)
            )
        }
    }

}

@Composable
fun CallUsageArea(
    modifier: Modifier = Modifier,
    image: String,
    name: String,
    phone: String,
    month: Int,
    percent: Float,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.3f),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileCard(image = image, name = name, phone = phone)
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 통화 사용량 그래프
                CallUsageDonutGraph()
            }
        }
        ColumnSpacer(height = 32.dp)
        Row(verticalAlignment = Alignment.Top) {
            Image(
                imageVector = IconPack.IcLoudspeaker,
                contentDescription = "ic_loudspeaker",
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(20.dp)
            )
            RowSpacer(width = 4.dp)
            Text(text = buildAnnotatedString {
                append("${month}달에 ${name}님과 ")
                withStyle(style = SpanStyle(color = PrimaryBlue2)) {
                    append("${percent}%")
                }
                append("의 통화 비중을 차지했어요.")
            }, style = Body1SemiBold.copy(Gray800))
        }

    }
}

@Composable
fun ColumnScope.CallUsageDonutGraph() {
    val textMeasurer = rememberTextMeasurer()

    val textToDraw = "13%"

    val textLayoutResult = remember(textToDraw, Body1Medium) {
        textMeasurer.measure(textToDraw, Body1Medium)
    }
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "12달 통화 사용량", style = Body2Medium.copy(Gray400))
        ColumnSpacer(height = 16.dp)
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        ) {

            drawArc(
                color = Gray150,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 20f)
            )
            drawArc(
                color = PrimaryBlue2,
                startAngle = -90f,
                sweepAngle = 70f,
                useCenter = false,
                style = Stroke(width = 20f)
            )
            drawText(
                textMeasurer = textMeasurer,
                text = textToDraw,
                style = Body1Medium.copy(PrimaryBlue2),
                topLeft = Offset(
                    x = center.x - textLayoutResult.size.width / 2,
                    y = center.y - textLayoutResult.size.height / 2,
                )
            )
        }
    }

}

@Composable
fun CallLogCountArea(month: Int, send: Int, receive: Int, name: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ColumnSpacer(height = 32.dp)
        Row(verticalAlignment = Alignment.Top) {
            Image(
                imageVector = IconPack.IcCall,
                contentDescription = "ic_loudspeaker",
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(20.dp)
            )
            RowSpacer(width = 4.dp)
            Text(text = "${month}달 통화 횟수", style = Body1SemiBold.copy(Gray800))
        }
        ColumnSpacer(height = 6.dp)
        Text(text = "\t\t\t\t수신: $receive", style = Body2Medium.copy(Gray500))
        ColumnSpacer(height = 2.dp)
        Text(text = "\t\t\t\t발신: $send", style = Body2Medium.copy(Gray500))
        ColumnSpacer(height = 24.dp)
        Row(verticalAlignment = Alignment.Top) {
            Image(
                imageVector = IconPack.IcSmileface,
                contentDescription = "ic_loudspeaker",
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(20.dp)
            )
            RowSpacer(width = 4.dp)
            if (send == 0 && receive == 0) {
                Text(
                    text = "${name}님과의 관계가 멀어지고 있어요.\n${name}님에게 연락해보세요!",
                    style = Body1SemiBold.copy(Gray800)
                )
            } else {
                val (dif, text) = if (send < receive) {
                    receive - send to "덜"
                } else {
                    send - receive to "많이"
                }
                Text(text = buildAnnotatedString {
                    append("${name}님보다 ")
                    withStyle(style = SpanStyle(color = PrimaryBlue2)) {
                        append("$dif")
                    }
                    append("번 $text 연락하셨습니다.\n${name}님에게 연락해보세요.")
                }, style = Body1SemiBold.copy(Gray800))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CallLogLineGraph(modifier: Modifier = Modifier, month: Int, data: List<Int>) {
    val textMeasurer = rememberTextMeasurer()

    val startDate = "${month}/1"
    val currentDate = "${month}/${LocalDate.now().dayOfMonth}"

    val startDateLayoutResult = remember(startDate, Body1Medium) {
        textMeasurer.measure(startDate, Body1Medium)
    }

    val currentDateLayoutResult = remember(currentDate, Body1Medium) {
        textMeasurer.measure(currentDate, Body1Medium)
    }
    val maximum = data.max().toFloat()
    val len = data.size.toFloat()
    Canvas(modifier = modifier.aspectRatio(2.0f)) {
        val lineGraphHeight = size.height - (startDateLayoutResult.size.height)*1.3f
        var (preX, preY) = 0f to lineGraphHeight
        data.forEachIndexed { index, h ->
            val curX = size.width * (index+1)/len
            val curY = lineGraphHeight * (1-h/maximum)
            drawLine(
                color = PrimaryBlue3,
                start = Offset(preX, preY),
                end = Offset(curX, curY),
                strokeWidth = 10f,
                cap = StrokeCap.Round
            )
            preX = curX
            preY = curY
        }
        drawCircle(color = PrimaryBlue3, center = Offset(preX, preY), radius = size.height / 40f)
        drawText(
            textMeasurer = textMeasurer,
            text = startDate,
            style = Body3Medium.copy(Gray400),
            topLeft = Offset(0f, size.height - startDateLayoutResult.size.height)
        )
        drawText(
            textMeasurer = textMeasurer,
            text = currentDate,
            style = Body3Medium.copy(Gray400),
            topLeft = Offset(size.width - currentDateLayoutResult.size.width, size.height - currentDateLayoutResult.size.height)
        )

    }
}