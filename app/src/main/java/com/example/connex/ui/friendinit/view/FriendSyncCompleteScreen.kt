package com.example.connex.ui.friendinit.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
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
import androidx.navigation.NavController
import com.example.connex.ui.Screen
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.theme.Heading1
import com.example.connex.utils.Constants

@Composable
fun FriendSyncCompleteScreen(applicationState: ApplicationState, userId: Long, name: String) {
//    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val bodyStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (20.8).sp,
        textAlign = TextAlign.Center,
        color = Color(0xFF939393)
    )


    Column(
        modifier = Modifier
            .padding(top = 100.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "${name}님, 반가워요!",
                style = Heading1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Connex와 함께 추억을 공유해 보세요.",
                style = bodyStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally){
            GeneralButton(
                modifier = Modifier
                    .height(55.dp)
                    .padding(horizontal = 24.dp),
                text = "추가하기",
                enabled = true
            ) {

                applicationState.navigatePopBackStack(Constants.INIT_SETTING_GRAPH, Screen.Home.route)
            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(text = "건너뛰기", style = body2Style, modifier = Modifier.clickable { navController.navigate(Constants.HOME_GRAPH) })
            Spacer(modifier = Modifier.height(46.dp))
        }
    }
}

