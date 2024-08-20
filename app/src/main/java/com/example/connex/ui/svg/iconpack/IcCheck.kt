package com.example.connex.ui.svg.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.connex.ui.svg.IconPack

public val IconPack.IcCheck: ImageVector
    get() {
        if (_iccheck != null) {
            return _iccheck!!
        }
        _iccheck = Builder(name = "Iccheck", defaultWidth = 15.0.dp, defaultHeight = 12.0.dp,
                viewportWidth = 15.0f, viewportHeight = 12.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.33333f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(1.4f, 5.3f)
                lineTo(6.65f, 10.55f)
                lineTo(13.0667f, 1.8f)
            }
        }
        .build()
        return _iccheck!!
    }

private var _iccheck: ImageVector? = null
