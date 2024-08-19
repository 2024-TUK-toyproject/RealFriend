package com.example.connex.ui.svg

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.connex.ui.svg.iconpack.ConnexLogoGreen
import com.example.connex.ui.svg.iconpack.ConnexLogoWhite
import com.example.connex.ui.svg.iconpack.Connexlogo2
import com.example.connex.ui.svg.iconpack.IcAddUser
import com.example.connex.ui.svg.iconpack.IcAlbumOff
import com.example.connex.ui.svg.iconpack.IcAlbumOn
import com.example.connex.ui.svg.iconpack.IcCall
import com.example.connex.ui.svg.iconpack.IcMenudot
import com.example.connex.ui.svg.iconpack.IcNotification
import com.example.connex.ui.svg.iconpack.IcSettingList
import com.example.connex.ui.svg.iconpack.myiconpack.IcCheck
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
            IcCheck
        )
        return __AllIcons!!
    }
