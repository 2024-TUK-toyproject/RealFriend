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

public val IconPack.IcLocation01: ImageVector
    get() {
        if (_icLocation01 != null) {
            return _icLocation01!!
        }
        _icLocation01 = Builder(name = "IcLocation01", defaultWidth = 18.0.dp, defaultHeight =
                18.0.dp, viewportWidth = 18.0f, viewportHeight = 18.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF303033)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(9.0f, 9.75f)
                curveTo(10.2426f, 9.75f, 11.25f, 8.7426f, 11.25f, 7.5f)
                curveTo(11.25f, 6.2574f, 10.2426f, 5.25f, 9.0f, 5.25f)
                curveTo(7.7574f, 5.25f, 6.75f, 6.2574f, 6.75f, 7.5f)
                curveTo(6.75f, 8.7426f, 7.7574f, 9.75f, 9.0f, 9.75f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF303033)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(9.0f, 16.5f)
                curveTo(12.0f, 13.5f, 15.0f, 10.8137f, 15.0f, 7.5f)
                curveTo(15.0f, 4.1863f, 12.3137f, 1.5f, 9.0f, 1.5f)
                curveTo(5.6863f, 1.5f, 3.0f, 4.1863f, 3.0f, 7.5f)
                curveTo(3.0f, 10.8137f, 6.0f, 13.5f, 9.0f, 16.5f)
                close()
            }
        }
        .build()
        return _icLocation01!!
    }

private var _icLocation01: ImageVector? = null
