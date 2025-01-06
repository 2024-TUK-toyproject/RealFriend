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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.connex.ui.album.view.TwoStickGraph
import com.example.connex.ui.album_setting.AlbumInfoViewModel
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.HorizontalGrayDivider
import com.example.connex.ui.component.ImageCard
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
import com.example.connex.utils.toFormatTime
import com.example.connex.utils.toMemorySizeAndUnit
import com.example.connex.utils.toWon

@Composable
fun AlbumInfoScreen(
    applicationState: ApplicationState,
    albumInfoViewModel: AlbumInfoViewModel = hiltViewModel(),
    albumId: String?,
) {
    val albumInfoState by albumInfoViewModel.albumInfo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        albumId?.let { albumInfoViewModel.fetchReadAlbumInfo(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        val scrollState = rememberScrollState()
        BackArrowAppBar(text = "앨범 정보") { applicationState.popBackStack() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(BackgroundGray)
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            ThumbnailOfAlbumArea(
                image = albumInfoState.albumThumbnail,
                title = albumInfoState.albumName,
                memberCount = albumInfoState.albumMemberInfo.size,
                maxMemberCount = albumInfoState.albumMemberMax
            )
            ColumnSpacer(height = 24.dp)
            AlbumInfoArea(
                founder = albumInfoState.albumFounder,
                foundDate = albumInfoState.albumFoundDate,
                // TODO(api 파라미터 추가 요청, 최대 구성원)
                totalPhotoQuantity = albumInfoState.albumPictureCount,
                myPhotoQuantity = albumInfoState.albumPictureCountFromCurrentUser,
                trashQuantity = albumInfoState.trashCount,
                currentUsage = albumInfoState.currentUsage,
                // TODO(api 파라미터 추가 요청, 내가 저장한 사진들의 용량)
                myCurrentUsage = albumInfoState.currentUsage,
                totalUsage = albumInfoState.totalUsage
            )
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
            ImageCard(image = image, weight = 0.4f)
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
                HorizontalGrayDivider(
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 15.dp
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
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
fun AlbumInfoArea(
    founder: String,
    foundDate: String,
    totalPhotoQuantity: Int,
    myPhotoQuantity: Int,
    trashQuantity: Int,
    currentUsage: Float,
    myCurrentUsage: Float,
    totalUsage: Float,
) {
    val(curSize, curUnit) = currentUsage.toLong().toMemorySizeAndUnit()
    val(myCurSize, myCurUnit) = myCurrentUsage.toLong().toMemorySizeAndUnit()
    val(totalSize, totalUnit) = totalUsage.toLong().toMemorySizeAndUnit()

    RoundedWhiteBox {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AlbumInfoRowContent(leftText = "생성자", rightText = founder)
            AlbumInfoRowContent(leftText = "생성 일자", rightText = foundDate)
            AlbumInfoRowContent(leftText = "전체 사진 수", rightText = "${totalPhotoQuantity.toWon()}개")
            AlbumInfoRowContent(leftText = "내가 업로드 한 사진 수", rightText = "${myPhotoQuantity.toWon()}개")
            AlbumInfoRowContent(leftText = "휴지통", rightText = "${trashQuantity.toWon()}개")
            ColumnSpacer(height = 8.dp)
            AlbumInfoColumnContent(
                firstText = "전체 용량",
                secondText = "$curSize$curUnit/$totalSize$totalUnit",
                activeQuantity = currentUsage,
                totalQuantity = totalUsage
            )
            AlbumInfoColumnContent(
                firstText = "내가 사용중인 용량",
                secondText = "$myCurSize$myCurUnit/$totalSize$totalUnit",
                activeQuantity = myCurrentUsage,
                totalQuantity = totalUsage
            )
        }
    }
}

@Composable
fun AlbumInfoRowContent(leftText: String, rightText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(text = leftText, style = Body2Medium)
        Text(text = rightText, style = Body2Medium)
    }
}

@Composable
fun AlbumInfoColumnContent(
    firstText: String,
    secondText: String,
    activeQuantity: Float,
    totalQuantity: Float,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = firstText,
            style = Body1Medium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        ColumnSpacer(height = 12.dp)
        TwoStickGraph(
            height = 20.dp,
            shape = CircleShape,
            color1 = BackgroundGray,
            color2 = PrimaryBlue2,
            sizePercent = activeQuantity / totalQuantity
        )
        ColumnSpacer(height = 5.dp)
        Text(
            text = secondText,
            style = Body3Medium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )
    }
}

