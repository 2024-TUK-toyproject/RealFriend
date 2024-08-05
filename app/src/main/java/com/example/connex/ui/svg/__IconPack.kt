package com.example.connex.ui.svg

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.connex.ui.svg.iconpack.Connexlogo2
import com.example.connex.ui.svg.iconpack.IcNotification
import kotlin.collections.List as ____KtList

public object IconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val IconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(IcNotification, Connexlogo2)
    return __AllIcons!!
  }
