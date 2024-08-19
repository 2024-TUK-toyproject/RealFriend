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
            R.drawable.ic_nav_home_on)

    object Friends :
        Screen(
            FRIENDS_ROUTE,
            "친구 목록",
            R.drawable.ic_nav_user_off,
            R.drawable.ic_nav_user_on,
        )

    object Album :
        Screen(
            ANALYZE_ROUTE,
            "앨범",
            R.drawable.ic_nav_album_off,
            R.drawable.ic_nav_album_on,
        )

    object Mypage :
        Screen(
            MYPAGE_ROUTE,
            "내 정보",
            R.drawable.ic_nav_setting_off,
            R.drawable.ic_nav_setting_on,
        )
}