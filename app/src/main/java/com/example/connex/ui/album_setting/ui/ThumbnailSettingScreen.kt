package com.example.connex.ui.album_setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.component.ColumnSpacer
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.component.RowSpacerWithWeight
import com.example.connex.ui.component.TempImageCard
import com.example.connex.ui.component.RoundedWhiteBox
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.theme.BackgroundGray

@Composable
fun ThumbnailSettingScreen(applicationState: ApplicationState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackArrowAppBar(text = "대표 이미지 변경") { applicationState.popBackStack() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(horizontal = 24.dp, vertical = 26.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RoundedWhiteBox {
                Column(
                    modifier = Modifier.fillMaxSize().padding(top = 60.dp, bottom = 28.dp, start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        RowSpacerWithWeight(weight = 0.15f)
//                        ImageCard(
//                            image = "https://shpbucket.s3.amazonaws.com/profile/default_profile/default.png",
//                            weight = 0.6f
//                        )
                        TempImageCard(weight = 0.7f)

                        RowSpacerWithWeight(weight = 0.15f)
                    }
                    Column {
                        GeneralButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(51.dp), text = "앨범에서 이미지 선택"
                        ) {}
                        ColumnSpacer(height = 21.dp)
                        GeneralButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(51.dp), text = "변경하기",
                            enabled = false
                        ) {}
                    }
                }

            }

        }
    }
}