package com.example.connex.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.connex.ui.Screen
import com.example.connex.ui.domain.ApplicationState
import com.example.connex.utils.Constants

/** BottomNavigation Bar를 정의한다. */
@Composable
fun BoxScope.BottomBar(
    appState: ApplicationState,
    bottomNavItems: List<Screen> = Constants.BOTTOM_NAV_ITEMS,
) {
    val nameStyle =
        TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 16.sp,
            color = Color.Black.copy(0.4f)
        )

    AnimatedVisibility(
        visible = appState.bottomBarState.value,
        enter = slideInVertically { it },
        exit = slideOutVertically { it },
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(color = Color.Transparent)
            .navigationBarsPadding(),
    ) {
        BottomNavigation(backgroundColor = Color.White, elevation = 0.dp) {
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
                                modifier = Modifier.padding(bottom = 8.dp)
                        )
                    },
                    label = { Text(text = screen.name, style = nameStyle) },
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