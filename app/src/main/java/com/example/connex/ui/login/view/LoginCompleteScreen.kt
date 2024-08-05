package com.example.connex.ui.login.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.connex.ui.component.GeneralButton
import com.example.connex.ui.theme.Heading1
import com.example.connex.ui.theme.Typography
import com.example.connex.utils.Constants

@Composable
fun LoginCompleteScreen(navController: NavController) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    val bodyStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (20.8).sp,
        textAlign = TextAlign.Center,
        color = Color(0xFF939393)
    )
    val body2Style = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (16.7).sp,
        color = Color(0xFF555555),
        textDecoration = TextDecoration.Underline,
    )


    Column(
        modifier = Modifier
            .padding(top = 148.dp - statusBarPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Text(
                text = "새싹님, 반가워요!",
                style = Heading1,
                modifier = Modifier.padding(horizontal = 104.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "연락 빈도를 기반으로 새싹님에게\n꼭 맞는 친구 목록을 추천해 드릴게요.",
                style = bodyStyle,
                modifier = Modifier.padding(horizontal = 70.dp)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally){
            GeneralButton(
                modifier = Modifier
                    .height(55.dp)
                    .padding(horizontal = 24.dp),
                text = "시작하기",
                enabled = true
            ) {
                navController.navigate(Constants.FRIEND_SYNC_ROUTE)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "건너뛰기", style = body2Style, modifier = Modifier.clickable { navController.navigate(Constants.HOME_GRAPH) })
            Spacer(modifier = Modifier.height(46.dp))
        }
    }
}