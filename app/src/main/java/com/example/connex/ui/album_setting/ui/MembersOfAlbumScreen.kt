package com.example.connex.ui.album_setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.HorizontalGrayDivider
import com.example.connex.ui.component.RowSpacerWithWeight
import com.example.connex.ui.component.TempImageCard
import com.example.connex.ui.component.RoundedWhiteBox
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.home.view.ContactCard
import com.example.connex.ui.theme.BackgroundGray
import com.example.connex.ui.theme.Body1SemiBold
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Body3Medium
import com.example.connex.ui.theme.Gray500
import com.example.connex.ui.theme.Gray800
import com.example.connex.ui.theme.Head3Medium
import com.example.connex.utils.Constants

@Composable
fun MembersOfAlbumScreen(applicationState: ApplicationState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackArrowAppBar(text = "대표 이미지 변경") { applicationState.popBackStack() }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(horizontal = 24.dp)
        ) {
            item { ColumnSpacer(height = 26.dp) }
            item { FounderArea(name = "누구게", granted = "founder") }
            item { ColumnSpacer(height = 36.dp) }
            item { Text(text = "멤버", style = Body3Medium, color = Gray500) }
            item { ColumnSpacer(height = 12.dp) }

            items(count = 10) {
                val (shape, isExistDivider) = when (it) {
                    0 -> { /* first 인 경우*/
                        RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp) to true
                    }

                    9 -> { /*  last 인 경우 */
                        RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp) to false
                    }

                    else -> {
                        RoundedCornerShape(0.dp) to true
                    }
                }
                MemberCard(member = "", shape = shape, isExistDivider = isExistDivider) { applicationState.navigate(Constants.ALBUM_INFO_MEMBER_GRANTED_SETTING_ROUTE) }
            }
            item { ColumnSpacer(height = 26.dp) }
        }
    }
}

@Composable
fun FounderArea(name: String, granted: String) {
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
                    text = "앨범 생성자",
                    style = Head3Medium,
                    color = Gray800,
                )
                ColumnSpacer(4.dp)
                Text(
                    text = name,
                    style = Body1SemiBold,
                    color = Gray800,
                )
                ColumnSpacer(4.dp)

                Text(
                    text = "권한: $granted",
                    style = Body2Medium,
                    color = Gray800,
                )
            }
            RowSpacerWithWeight(weight = 0.1f)

        }
    }
}

@Composable
fun LazyItemScope.MemberCard(member: Any, shape: Shape, isExistDivider: Boolean = true, onClick: () -> Unit) {
    RoundedWhiteBox(shape = shape) {
        Box {
            ContactCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {onClick()}
                    .padding(start = 16.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
                size = 40.dp,
                image = Constants.DEFAULT_PROFILE,
                name = "누구게",
                phone = "010-5678-1234"
            ) {
                Text(text = "visitor")
            }
            if (isExistDivider) {
                HorizontalGrayDivider(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 16.dp)
                )
            }
        }

    }
}
