package com.example.connex.ui.notification.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.connex.ui.component.BackArrowAppBar
import com.example.connex.ui.notification.NotificationViewModel
import com.example.connex.ui.theme.Body1Semibold
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray600
import com.example.connex.ui.theme.PrimaryBlue1
import kotlinx.coroutines.launch

@Composable
fun NotificationScreen(
    navController: NavController,
    notificationViewModel: NotificationViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { BackArrowAppBar(text = "알림") { navController.popBackStack() } }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val list = listOf("활동", "친구")
            val pagerState = rememberPagerState {
                list.size
            }
            NotificationTabRow(pagerState = pagerState, list = list)
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
                when (it) {
                    0 -> NotifiActivityScreen()
                    1 -> NotifiFriendScreen()
                }
            }
        }
    }
}

@Composable
fun NotificationTabRow(modifier: Modifier = Modifier, pagerState: PagerState, list: List<String>) {
    val scope = rememberCoroutineScope()

    TabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = list.size,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                color = PrimaryBlue1, // 인디케이터 색상 변경
                height = 2.dp
            )
        },
        containerColor = Color.Transparent,
        contentColor = Gray600
    ) {
        list.forEachIndexed { index, s ->
            Tab(
//                modifier = Modifier.width(36.dp),
                selected = pagerState.currentPage == index,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                text = {
                    Text(
                        text = s,
                        style = Body1Semibold.copy(color = if (pagerState.currentPage != index) Gray300 else Gray600),
                    )
                })
        }
    }
}