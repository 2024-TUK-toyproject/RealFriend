package com.example.connex.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.connex.ui.Screen
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.ui.theme.Btn11ptMedium
import com.example.connex.ui.theme.Gray100
import com.example.connex.ui.theme.Gray300
import com.example.connex.ui.theme.Gray600
import com.example.connex.ui.theme.PrimaryBlue1
import com.example.connex.utils.Constants

/** BottomNavigation Bar를 정의한다. */
@Composable
fun BoxScope.BottomBar(
    appState: ApplicationState,
    bottomNavItems: List<Screen> = Constants.BOTTOM_NAV_ITEMS,
) {
    fun nameStyle(isSelected: Boolean) =
        if (isSelected) Btn11ptMedium.copy(color = PrimaryBlue1) else Btn11ptMedium.copy(Gray300)

    AnimatedVisibility(
        visible = appState.bottomBarState.value,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(color = Color.Transparent),
    ) {
        BottomNavigation(
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(
                        color = Color(0x0D000000),
                        cornerRadius = CornerRadius(20.dp.toPx(), 20.dp.toPx()),
                        style = Stroke(width = 1f)
                    )
                }
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                ),
            backgroundColor = Color.White,
        ) {
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomNavItems.forEachIndexed { _, screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(
                                id =
                                (if (isSelected) screen.selecteddrawableResId else screen.drawableResId),
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(bottom = 5.dp),
                        )
                    },
                    label = { Text(text = screen.name, style = nameStyle(isSelected)) },
                    selected = isSelected,
                    modifier = Modifier
//                        .width(24.dp)
                        .padding(vertical = 4.dp, horizontal = 14.dp)
                        .background(Color.Transparent, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp)),
                    onClick = {
                        appState.navController.navigate(screen.route) {
                            popUpTo(Constants.HOME_GRAPH) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = Color.Unspecified,
                    unselectedContentColor = Color.Unspecified,
                )
            }
        }
    }

}

@Composable
fun BoxScope.AlbumSelectModeBottomBar(
    isVisible: Boolean = false,
    onDownload: () -> Unit,
    onDelete: () -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(color = Color.Transparent),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = Gray100,
                        start = Offset.Zero,
                        end = Offset(x = size.width, y = 0f),
                        strokeWidth = 1f
                    )
                }
                .background(color = Color.White)
                .padding(horizontal = 38.dp, vertical = 9.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(icon = Icons.Outlined.Download, text = "다운로드") { onDownload() }
            BottomNavItem(icon = Icons.Outlined.Share, text = "공유하기") {}
            BottomNavItem(icon = Icons.Outlined.PlayCircleOutline, text = "슬라이드쇼") {}
            BottomNavItem(icon = Icons.Outlined.Delete, text = "삭제하기") { onDelete() }
        }
//        BottomNavigation(
//            modifier = Modifier
//                .drawBehind {
//                    drawLine(
////                        color = Gray100,
//                        color = Color.Red,
//                        start = Offset.Zero,
//                        end = Offset(x = size.width, y = 0f),
//                        strokeWidth = 1f
//                    )
//                }
//                .padding(horizontal = 38.dp, vertical = 9.dp),
//            backgroundColor = Color.White,
//        ) {
//            BottomNavItem(icon = Icons.Outlined.Download, text = "다운로드") { onDownload() }
//            BottomNavItem(icon = Icons.Outlined.Share, text = "공유하기") {}
//            BottomNavItem(icon = Icons.Outlined.PlayCircleOutline, text = "슬라이드쇼") {}
//            BottomNavItem(icon = Icons.Outlined.Delete, text = "삭제하기") { onDelete() }
        }
    }
//}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
) {
    val textStyle = TextStyle(
        fontSize = 11.sp,
        lineHeight = 15.4.sp,
        color = Gray600
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "ic_bottom_nav",
            modifier = Modifier.size(20.dp),
            tint = Gray600
        )
        ColumnSpacer(height = 3.dp)
        Text(text = text, style = textStyle)
    }
}