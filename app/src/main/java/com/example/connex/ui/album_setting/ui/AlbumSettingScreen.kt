package com.example.connex.ui.album_setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.connex.ui.album_setting.ui.SettingScreenInfo.Companion.FIRST_SCREEN_TITLE_SIZE
import com.example.connex.ui.album_setting.ui.SettingScreenInfo.Companion.SECOND_SCREEN_TITLE_SIZE
import com.example.connex.ui.album_setting.ui.SettingScreenInfo.Companion.THIRD_SCREEN_TITLE_SIZE
import com.example.connex.ui.component.AlbumGetOutModalBottomSheet
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ClickableRowContent
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.HorizontalGrayDivider
import com.example.connex.ui.component.RoundedWhiteBox
import com.example.connex.ui.component.SwitchRowContent
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.theme.BackgroundGray
import com.example.connex.ui.theme.PrimaryRed
import com.example.connex.utils.Constants

data class SettingScreenInfo(
    val title: String,
    val onClick: () -> Unit,
) {
    companion object {
        const val FIRST_SCREEN_TITLE_SIZE = 1
        const val SECOND_SCREEN_TITLE_SIZE = 5
        const val THIRD_SCREEN_TITLE_SIZE = 2
    }
}


@Composable
fun AlbumSettingScreen(applicationState: ApplicationState) {

    var isShowAlbumGetOutBottomSheet by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        if (isShowAlbumGetOutBottomSheet) {
            AlbumGetOutModalBottomSheet(onClose = {isShowAlbumGetOutBottomSheet = false}) {

            }
        }
        val scrollState = rememberScrollState()
        BackArrowAppBar(text = "앨범 설정") {}
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(BackgroundGray)
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            val settingScreens = listOf(
                SettingScreenInfo("앨범 정보") {applicationState.navigate(Constants.ALBUM_INFO_ROUTE)},
                SettingScreenInfo("대표 이미지 변경") {applicationState.navigate(Constants.ALBUM_INFO_THUMBNAIL_ROUTE)},
                SettingScreenInfo("멤버 보기") {},
                SettingScreenInfo("멤버 권한 설정") {applicationState.navigate(Constants.ALBUM_INFO_MEMBER_ROUTE)},
                SettingScreenInfo("멤버 초대하기") {},
                SettingScreenInfo("휴지통") {},
                SettingScreenInfo("앨범 편집 알림") {},
                SettingScreenInfo("멤버 변경 알림") {},
                SettingScreenInfo("공유 앨범 나가기") {isShowAlbumGetOutBottomSheet = true},
            )
            RoundedWhiteBox {
                val firstSettingScreen = settingScreens.first()
                ClickableRowContent(text = firstSettingScreen.title) { firstSettingScreen.onClick() }
            }
            ColumnSpacer(height = 24.dp)

            RoundedWhiteBox {
                val secondSettingScreen =
                    settingScreens
                        .slice(FIRST_SCREEN_TITLE_SIZE..SECOND_SCREEN_TITLE_SIZE)
                Column {
                    secondSettingScreen.forEachIndexed { index, settingScreenInfo ->
                        ClickableRowContent(text = settingScreenInfo.title, navIcon = true) {settingScreenInfo.onClick()}
                        if (index != secondSettingScreen.lastIndex) {
                            HorizontalGrayDivider(modifier = Modifier.padding(horizontal = 16.dp))
                        }
                    }
                }

            }

            ColumnSpacer(height = 24.dp)

            RoundedWhiteBox {
                val thirdSettingScreen =
                    settingScreens
                        .slice(FIRST_SCREEN_TITLE_SIZE + SECOND_SCREEN_TITLE_SIZE..SECOND_SCREEN_TITLE_SIZE + THIRD_SCREEN_TITLE_SIZE)
                thirdSettingScreen.forEachIndexed { index, settingScreenInfo ->
                    SwitchRowContent(text = settingScreenInfo.title)
                    if (index != thirdSettingScreen.lastIndex) {
                        HorizontalGrayDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }

            ColumnSpacer(height = 24.dp)

            RoundedWhiteBox {
                val fourthSettingScreen = settingScreens.last()
                ClickableRowContent(text = fourthSettingScreen.title, textColor = PrimaryRed) {fourthSettingScreen.onClick()}
            }
        }

    }

}