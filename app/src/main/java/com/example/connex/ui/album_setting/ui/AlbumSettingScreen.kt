package com.example.connex.ui.album_setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.connex.ui.album_setting.ui.SettingScreenInfo.Companion.FIRST_SCREEN_TITLE_SIZE
import com.example.connex.ui.album_setting.ui.SettingScreenInfo.Companion.SECOND_SCREEN_TITLE_SIZE
import com.example.connex.ui.album_setting.ui.SettingScreenInfo.Companion.THIRD_SCREEN_TITLE_SIZE
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.util.ClickableRowContent
import com.example.connex.ui.component.util.RoundedWhiteBox
import com.example.connex.ui.component.util.SwitchRowContent
import com.example.connex.ui.theme.BackgroundGray
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.PrimaryRed

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
fun AlbumSettingScreen() {
    val scope = rememberCoroutineScope()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
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
                SettingScreenInfo("앨범 정보") {},
                SettingScreenInfo("대표 이미지 변경") {},
                SettingScreenInfo("멤버 보기") {},
                SettingScreenInfo("멤버 권한 설정") {},
                SettingScreenInfo("멤버 초대하기") {},
                SettingScreenInfo("휴지통") {},
                SettingScreenInfo("앨범 편집 알림") {},
                SettingScreenInfo("멤버 변경 알림") {},
                SettingScreenInfo("공유 앨범 나가기") {},
            )
            RoundedWhiteBox {
                val firstSettingScreen = settingScreens.first()
                ClickableRowContent(text = firstSettingScreen.title) { firstSettingScreen.onClick }
            }
            ColumnSpacer(height = 24.dp)

            RoundedWhiteBox {
                val secondSettingScreen =
                    settingScreens
                        .slice(FIRST_SCREEN_TITLE_SIZE..SECOND_SCREEN_TITLE_SIZE)
                secondSettingScreen.forEachIndexed { index, settingScreenInfo ->
                    ClickableRowContent(text = settingScreenInfo.title, navIcon = true) {}
                    if (index != secondSettingScreen.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = Gray100
                        )
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
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = Gray100
                        )
                    }
                }
            }

            ColumnSpacer(height = 24.dp)

            RoundedWhiteBox {
                val fourthSettingScreen = settingScreens.last()
                ClickableRowContent(text = fourthSettingScreen.title, textColor = PrimaryRed) {}
            }
        }

    }

}