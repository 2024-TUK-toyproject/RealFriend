package com.example.connex.ui.album_setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.connex.ui.album.view.TwoStickGraph
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.HorizontalGrayDivider
import com.example.connex.ui.component.RowSpacerWithWeight
import com.example.connex.ui.component.TempImageCard
import com.example.connex.ui.component.RoundedWhiteBox
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.theme.BackgroundGray
import com.example.connex.ui.theme.Body1Medium
import com.example.connex.ui.theme.Body1SemiBold
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.PrimaryBlue2

@Composable
fun AlbumInfoScreen(applicationState: ApplicationState) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        val scrollState = rememberScrollState()
        BackArrowAppBar(text = "앨범 정보") {applicationState.popBackStack()}
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(BackgroundGray)
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            ThumbnailOfAlbumArea(
                image = "https://shpbucket.s3.amazonaws.com/profile/default_profile/default.png",
                title = "돼지와 함께 춤을",
                memberCount = 2,
                maxMemberCount = 10
            )
            ColumnSpacer(height = 24.dp)
            AlbumInfoArea()
        }
    }
}

@Composable
fun ThumbnailOfAlbumArea(image: String, title: String, memberCount: Int, maxMemberCount: Int) {
    RoundedWhiteBox {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            ImageCard(image = image, weight = 0.4f)
            TempImageCard(weight = 0.4f)
            RowSpacerWithWeight(weight = 0.1f)
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = Body1SemiBold,
                    color = Gray800,
                )
                HorizontalGrayDivider(modifier = Modifier.padding(horizontal = 8.dp, vertical = 15.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = "구성원", style = Body2Medium)
                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = PrimaryBlue2)) {
                            append(memberCount.toString())
                        }
                        append(" / $maxMemberCount")
                    }, style = Body1SemiBold)
                }
            }
            RowSpacerWithWeight(weight = 0.1f)

        }
    }
}

@Composable
fun AlbumInfoArea() {
    RoundedWhiteBox {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            AlbumInfoRowContent(leftText = "생성자", rightText = "정대영")
            AlbumInfoRowContent(leftText = "생성 일자", rightText = "2000.11.20")
            AlbumInfoRowContent(leftText = "전체 사진 수", rightText = "1,001개")
            AlbumInfoRowContent(leftText = "내가 업로드 한 사진 수", rightText = "130개")
            AlbumInfoRowContent(leftText = "휴지통", rightText = "13개")
            ColumnSpacer(height = 8.dp)
            AlbumInfoColumnContent(firstText = "전체 용량", secondText = "7GB/10GB", activeQuantity = 7, totalQuantity = 10)
            AlbumInfoColumnContent(firstText = "내가 사용중인 용량", secondText = "3GB/10GB", activeQuantity = 3, totalQuantity = 10)
        }
    }
}

@Composable
fun AlbumInfoRowContent(leftText: String, rightText: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 2.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
        Text(text = leftText, style = Body2Medium)
        Text(text = rightText, style = Body2Medium)
    }
}
@Composable
fun AlbumInfoColumnContent(firstText: String, secondText: String, activeQuantity: Int, totalQuantity: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = firstText, style = Body1Medium, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        ColumnSpacer(height = 12.dp)
        TwoStickGraph(
            height = 20.dp,
            shape = CircleShape,
            color1 = BackgroundGray,
            color2 = PrimaryBlue2,
            sizePercent = activeQuantity / totalQuantity.toFloat()
        )
        ColumnSpacer(height = 5.dp)
        Text(text = secondText, style = Body3Medium, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End)
    }
}

