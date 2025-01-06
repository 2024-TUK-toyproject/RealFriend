package com.example.connex.utils

import androidx.compose.ui.unit.dp
import com.example.connex.ui.Screen

object Constants {
    val BottomNavigationHeight = 56.dp

    val BOTTOM_NAV_ITEMS = listOf<Screen>(Screen.Home, Screen.Friends,Screen.Album, Screen.Mypage)

    val DEFAULT_PROFILE = "https://shpbucket.s3.amazonaws.com/profile/default_profile/default.png"

    val CONNEX_DATASTORE = "connex-datastore"

    val DEEP_LINK_URI = "Connex://"

    /** navigation id */
    const val HOME_ROUTE = "nav-home"
    const val FRIENDS_ROUTE = "nav-friends"
    const val ANALYZE_ROUTE = "nav-analyze"
    const val MYPAGE_ROUTE = "nav-mypage"

    const val NOTIFICATION_ROUTE = "nav-notification"

    const val FRIEND_REMOVE_ROUTE = "nav-friend-remove"
    const val FRIEND_ADD_ROUTE = "nav-friend-add"


    const val WEB_VIEW_ROUTE = "nav-web-view"

    const val EDIT_CATEGORY_ROUTE = "nav-edit_category"
    const val HOME_ALL_STORE_ROUTE = "nav-home-all-store"

    const val EDIT_PROFILE_IMAGE_ROUTE = "nav-edit_profile_image"
    const val MYPAGE_EDIT_PROFILE_ROUTE = "nav-mypage-edit-profile"
    const val MYPAGE_EDIT_PROFILE_COMPLETE_ROUTE = "nav-mypage-edit-profile-complete"

    const val SIGNUP_START_ROUTE = "nav-login"
    const val SIGNUP_PROFILE_INIT_ROUTE = "nav-profile-init"
    const val SIGNUP_COMPLETE_ROUTE = "nav-complete"
    const val SPLASH_ROUTE = "nav-splash"

    const val FRIEND_SYNC_ROUTE = "nav-friend-sync"
    const val FRIEND_SYNC_COMPLETE_ROUTE = "nav-friend-sync-complete"

    const val ALBUM_CREATING_ROUTE = "nav-album-creating"
    const val ALBUM_CREATING_SELECT_FRIEND_ROUTE = "nav-album-creating-select-friend"
    const val ALBUM_CREATING_SELECT_BACKGROUND_ROUTE = "nav-album-creating-select-background"
    const val ALBUM_CREATING_SETTING_NAME_ROUTE = "nav-album-creating-select-name"
    const val ALBUM_CREATING_COMPLETE_ROUTE = "nav-album-creating-complete"

    const val ALBUM_INFO_PHOTO_LIST_ROUTE = "nav-album-info-photo-list"
    const val ALBUM_INFO_PHOTO_ROUTE = "nav-album-info-photo"
    const val ALBUM_INFO_PHOTO_COMMENT_ROUTE = "nav-album-info-photo-comment"
    const val ALBUM_INFO_PHOTO_ADD_ROUTE = "nav-album-info-photo-add"


    const val ALBUM_SETTING_ROUTE = "nav-album-setting"
    const val ALBUM_INFO_ROUTE = "nav-album-info"
    const val ALBUM_INFO_THUMBNAIL_ROUTE = "nav-album-info-thumbnail"
    const val ALBUM_INFO_MEMBER_ROUTE = "nav-album-info-member"
    const val ALBUM_INFO_MEMBER_GRANTED_SETTING_ROUTE = "nav-album-info-member-granted-setting"
    const val ALBUM_INFO_MEMBER_ADD_ROUTE = "nav-album-info-member-add"

    const val RECOMMENDED_CONTACT_LOGGING_ROUTE = "nav-recommended-contact-logging"
    const val RECOMMENDED_CONTACT_RESULT_ROUTE = "nav-recommended-contact-result"



    const val SIGNIN_AGREEMENT_ROUTE = "nav-signin-agreement"
    const val SIGNIN_PHONE_VERIFY_ROUTE = "nav-signin-phone-verify"
    const val SIGNIN_USER_VERIFICATION_ROUTE = "nav-signin-user-verification"
    const val SIGNIN_PASSWORD_ROUTE = "nav-signin-password"
    const val SIGNIN_AGREEMENT_DETAIL_ROUTE = "nav-signin-agreement-detail"
    const val SIGNIN_PROFILE_IMAGE_ROUTE = "nav-signin-profile-image"
    const val SIGNIN_CATEGORY_ROUTE = "nav-signin-category"
    const val SIGNIN_COMPLETE_ROUTE = "nav-signin-complete"

    const val DETAIL_ROUTE = "nav-detail"
    const val REVIEW_WRITE_ROUTE = "nav-review-write-review"
    const val REVIEW_DETAIL_ROUTE = "nav-review_detail-review"
    const val REVIEW_REPORT_ROUTE = "nav-review_report-review"

    const val PASSWORD_SEARCH_PHONE_ROUTE = "nav-password-search-phone"
    const val PASSWORD_SEARCH_PHONE_VERIFY_ROUTE = "nav-password-search-phone-verify"
    const val PASSWORD_SEARCH_PHONE_CHANGE_PASSWORD = "nav-password-search-phone-change-password"

    const val SETTING_MAIN_ROUTE = "nav-setting-main"
    const val SETTING_PERSONAL_INFO_MANAGEMENT_ROUTE = "nav-setting-personal-info-manamgent"
    const val SETTING_WITHDRAWAL_ROUTE = "nav-setting-withdrawal"
    const val SETTING_EDIT_PASSWORD_ROUTE = "nav-setting-edit-password"
    const val SETTING_VERIFY_PASSWORD_ROTUE = "nav-setting-verify-password"

    /** Graph Id */
    const val HOME_GRAPH = "home-graph"
    const val LOGIN_GRAPH = "login-graph"
    const val INIT_SETTING_GRAPH = "init-setting-graph"
    const val ALBUM_CREATE_START_GRAPH = "album-create-start-graph"
    const val ALBUM_CREATING_GRAPH = "album-creating-graph"
    const val ALBUM_INFO_GRAPH = "album-info-graph"
    const val ALBUM_SETTING_GRAPH = "album-setting-graph"
    const val RECOMMENDED_CONTACT_GRAPH = "album-recommended-contact-graph"
    const val SETTING_GRAPH = "setting-graph"
    const val SIGNIN_GRAPH = "signin-graph"
    const val DETAIL_GRAPH = "detail-graph"
    const val REVIEW_DETAIL_GRAPH = "review-detail-graph"
    const val PASSWORD_SEARCH_GRAPH = "password-search-graph"
}