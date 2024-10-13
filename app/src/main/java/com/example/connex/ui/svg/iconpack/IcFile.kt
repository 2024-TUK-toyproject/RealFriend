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

public val IconPack.IcFile: ImageVector
    get() {
        if (_icFile != null) {
            return _icFile!!
        }
        _icFile = Builder(name = "IcFile", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
                viewportWidth = 18.0f, viewportHeight = 18.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF303033)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.5f, 1.7021f)
                verticalLineTo(4.8f)
                curveTo(10.5f, 5.22f, 10.5f, 5.4301f, 10.5817f, 5.5905f)
                curveTo(10.6537f, 5.7316f, 10.7684f, 5.8464f, 10.9095f, 5.9183f)
                curveTo(11.0699f, 6.0f, 11.28f, 6.0f, 11.7f, 6.0f)
                horizontalLineTo(14.7979f)
                moveTo(15.0f, 7.4912f)
                verticalLineTo(12.9f)
                curveTo(15.0f, 14.1601f, 15.0f, 14.7902f, 14.7548f, 15.2715f)
                curveTo(14.539f, 15.6948f, 14.1948f, 16.039f, 13.7715f, 16.2548f)
                curveTo(13.2902f, 16.5f, 12.6601f, 16.5f, 11.4f, 16.5f)
                horizontalLineTo(6.6f)
                curveTo(5.3399f, 16.5f, 4.7098f, 16.5f, 4.2285f, 16.2548f)
                curveTo(3.8052f, 16.039f, 3.4609f, 15.6948f, 3.2452f, 15.2715f)
                curveTo(3.0f, 14.7902f, 3.0f, 14.1601f, 3.0f, 12.9f)
                verticalLineTo(5.1f)
                curveTo(3.0f, 3.8399f, 3.0f, 3.2098f, 3.2452f, 2.7285f)
                curveTo(3.4609f, 2.3052f, 3.8052f, 1.961f, 4.2285f, 1.7452f)
                curveTo(4.7098f, 1.5f, 5.3399f, 1.5f, 6.6f, 1.5f)
                horizontalLineTo(9.0088f)
                curveTo(9.5592f, 1.5f, 9.8343f, 1.5f, 10.0933f, 1.5622f)
                curveTo(10.3229f, 1.6173f, 10.5423f, 1.7082f, 10.7436f, 1.8316f)
                curveTo(10.9707f, 1.9707f, 11.1653f, 2.1653f, 11.5544f, 2.5544f)
                lineTo(13.9456f, 4.9456f)
                curveTo(14.3347f, 5.3347f, 14.5293f, 5.5293f, 14.6684f, 5.7564f)
                curveTo(14.7918f, 5.9577f, 14.8827f, 6.1771f, 14.9378f, 6.4067f)
                curveTo(15.0f, 6.6657f, 15.0f, 6.9408f, 15.0f, 7.4912f)
                close()
            }
        }
        .build()
        return _icFile!!
    }

private var _icFile: ImageVector? = null
