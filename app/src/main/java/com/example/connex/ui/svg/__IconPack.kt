package com.example.connex.ui.svg

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.connex.ui.svg.iconpack.ConnexLogoGreen
import com.example.connex.ui.svg.iconpack.ConnexLogoWhite
import com.example.connex.ui.svg.iconpack.Connexlogo2
import com.example.connex.ui.svg.iconpack.IcAddUser
import com.example.connex.ui.svg.iconpack.IcAlbumOff
import com.example.connex.ui.svg.iconpack.IcAlbumOn
import com.example.connex.ui.svg.iconpack.IcBlackWarning
import com.example.connex.ui.svg.iconpack.IcCall
import com.example.connex.ui.svg.iconpack.IcCheck
import com.example.connex.ui.svg.iconpack.IcConnexUsedUser
import com.example.connex.ui.svg.iconpack.IcFavoriteOn
import com.example.connex.ui.svg.iconpack.IcMenudot
import com.example.connex.ui.svg.iconpack.IcNotification
import com.example.connex.ui.svg.iconpack.IcSettingList
import com.example.connex.ui.svg.iconpack.IcRedWarning
import com.example.connex.ui.svg.iconpack.ImageMail
import com.example.connex.ui.svg.iconpack.IcFavoriteOff
import com.example.connex.ui.svg.iconpack.IcCalendar
import com.example.connex.ui.svg.iconpack.IcFile
import com.example.connex.ui.svg.iconpack.IcImage
import com.example.connex.ui.svg.iconpack.IcLocation01
import com.example.connex.ui.svg.iconpack.IcLoudspeaker
import com.example.connex.ui.svg.iconpack.IcSend
import com.example.connex.ui.svg.iconpack.IcSmileface
import com.example.connex.ui.svg.iconpack.ImageGrantedFounder
import com.example.connex.ui.svg.iconpack.ImageGrantedManager
import com.example.connex.ui.svg.iconpack.ImageGrantedMember
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(
            IcNotification,
            Connexlogo2,
            IcAddUser,
            ConnexLogoGreen,
            ConnexLogoWhite,
            IcCall,
            IcAlbumOn,
            IcAlbumOff,
            IcMenudot,
            IcSettingList,
            IcCheck,
            IcRedWarning,
            ImageMail,
            IcConnexUsedUser,
            IcBlackWarning,
            IcFavoriteOn,
            IcFavoriteOff,
            IcImage, IcCalendar, IcFile, IcLocation01, IcSend,
            ImageGrantedFounder, ImageGrantedManager, ImageGrantedMember,
            IcLoudspeaker, IcCall, IcSmileface
        )
        return __AllIcons!!
    }
