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

public val IconPack.IcCalendar: ImageVector
    get() {
        if (_icCalendar != null) {
            return _icCalendar!!
        }
        _icCalendar = Builder(name = "IcCalendar", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
                viewportWidth = 18.0f, viewportHeight = 18.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF303033)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(15.75f, 7.5f)
                horizontalLineTo(2.25f)
                moveTo(12.0f, 1.5f)
                verticalLineTo(4.5f)
                moveTo(6.0f, 1.5f)
                verticalLineTo(4.5f)
                moveTo(5.85f, 16.5f)
                horizontalLineTo(12.15f)
                curveTo(13.4101f, 16.5f, 14.0402f, 16.5f, 14.5215f, 16.2548f)
                curveTo(14.9448f, 16.039f, 15.289f, 15.6948f, 15.5048f, 15.2715f)
                curveTo(15.75f, 14.7902f, 15.75f, 14.1601f, 15.75f, 12.9f)
                verticalLineTo(6.6f)
                curveTo(15.75f, 5.3399f, 15.75f, 4.7098f, 15.5048f, 4.2285f)
                curveTo(15.289f, 3.8052f, 14.9448f, 3.4609f, 14.5215f, 3.2452f)
                curveTo(14.0402f, 3.0f, 13.4101f, 3.0f, 12.15f, 3.0f)
                horizontalLineTo(5.85f)
                curveTo(4.5899f, 3.0f, 3.9598f, 3.0f, 3.4785f, 3.2452f)
                curveTo(3.0552f, 3.4609f, 2.7109f, 3.8052f, 2.4952f, 4.2285f)
                curveTo(2.25f, 4.7098f, 2.25f, 5.3399f, 2.25f, 6.6f)
                verticalLineTo(12.9f)
                curveTo(2.25f, 14.1601f, 2.25f, 14.7902f, 2.4952f, 15.2715f)
                curveTo(2.7109f, 15.6948f, 3.0552f, 16.039f, 3.4785f, 16.2548f)
                curveTo(3.9598f, 16.5f, 4.5899f, 16.5f, 5.85f, 16.5f)
                close()
            }
        }
        .build()
        return _icCalendar!!
    }

private var _icCalendar: ImageVector? = null
