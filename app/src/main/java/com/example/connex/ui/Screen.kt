package com.example.connex.ui

import androidx.annotation.DrawableRes
import com.example.connex.R
import com.example.connex.utils.Constants.ANALYZE_ROUTE
import com.example.connex.utils.Constants.FRIENDS_ROUTE
import com.example.connex.utils.Constants.HOME_ROUTE
import com.example.connex.utils.Constants.MYPAGE_ROUTE

sealed class Screen(
    val route: String,
    val name: String,
    @DrawableRes val drawableResId: Int,
    @DrawableRes val selecteddrawableResId: Int
) {
    object Home :
        Screen(
            HOME_ROUTE,
            "홈",
            R.drawable.ic_nav_home_off,
            R.drawable.ic_nav_home_off,
        )

    object Friends :
        Screen(
            FRIENDS_ROUTE,
            "친구 목록",
            R.drawable.ic_nav_friends_off,
            R.drawable.ic_nav_friends_off,
        )

    object Analyze :
        Screen(
            ANALYZE_ROUTE,
            "분석",
            R.drawable.ic_nav_analyze_off,
            R.drawable.ic_nav_analyze_off,
        )

    object Mypage :
        Screen(
            MYPAGE_ROUTE,
            "내 정보",
            R.drawable.ic_nav_mypage_off,
            R.drawable.ic_nav_mypage_off,
        )
}