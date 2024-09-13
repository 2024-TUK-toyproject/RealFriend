package com.example.connex.ui.album_create.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.theme.Body1Regular
import com.example.connex.ui.theme.Body2Medium
import com.example.connex.ui.theme.Gray400
import com.example.connex.ui.theme.Heading1
import com.example.connex.utils.Constants

@Composable
fun CreatingAlbumCompleteScreen(applicationState: ApplicationState) {

    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .navigationBarsPadding()
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "공유 앨범이 생성됐어요!",
                style = Heading1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "사진을 업로드하고 친구들과\n함께했던 추억을 공유해 보세요.",
                style = Body1Regular,
                color = Gray400,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally){
            GeneralButton(
                modifier = Modifier
                    .height(55.dp)
                    .padding(horizontal = 24.dp),
                text = "앨범 바로가기",
                enabled = true
            ) {
//                applicationState.navigate("${Constants.FRIEND_SYNC_ROUTE}/$userId/$name")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "홈으로 가기", style = Body2Medium, modifier = Modifier.clickable { applicationState.navigatePopBackStack(Constants.HOME_ROUTE) })
        }
    }
}