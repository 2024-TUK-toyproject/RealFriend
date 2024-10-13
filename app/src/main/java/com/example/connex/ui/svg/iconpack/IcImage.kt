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

public val IconPack.IcImage: ImageVector
    get() {
        if (_icImage != null) {
            return _icImage!!
        }
        _icImage = Builder(name = "IcImage", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
                viewportWidth = 18.0f, viewportHeight = 18.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF303033)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.15f, 15.75f)
                horizontalLineTo(5.1985f)
                curveTo(4.7442f, 15.75f, 4.517f, 15.75f, 4.4118f, 15.6602f)
                curveTo(4.3205f, 15.5822f, 4.2721f, 15.4652f, 4.2815f, 15.3456f)
                curveTo(4.2923f, 15.2077f, 4.453f, 15.047f, 4.7743f, 14.7257f)
                lineTo(11.1515f, 8.3485f)
                curveTo(11.4485f, 8.0515f, 11.597f, 7.903f, 11.7682f, 7.8474f)
                curveTo(11.9189f, 7.7984f, 12.0811f, 7.7984f, 12.2318f, 7.8474f)
                curveTo(12.403f, 7.903f, 12.5515f, 8.0515f, 12.8485f, 8.3485f)
                lineTo(15.75f, 11.25f)
                verticalLineTo(12.15f)
                moveTo(12.15f, 15.75f)
                curveTo(13.4101f, 15.75f, 14.0402f, 15.75f, 14.5215f, 15.5048f)
                curveTo(14.9448f, 15.289f, 15.289f, 14.9448f, 15.5048f, 14.5215f)
                curveTo(15.75f, 14.0402f, 15.75f, 13.4101f, 15.75f, 12.15f)
                moveTo(12.15f, 15.75f)
                horizontalLineTo(5.85f)
                curveTo(4.5899f, 15.75f, 3.9598f, 15.75f, 3.4785f, 15.5048f)
                curveTo(3.0552f, 15.289f, 2.7109f, 14.9448f, 2.4952f, 14.5215f)
                curveTo(2.25f, 14.0402f, 2.25f, 13.4101f, 2.25f, 12.15f)
                verticalLineTo(5.85f)
                curveTo(2.25f, 4.5899f, 2.25f, 3.9598f, 2.4952f, 3.4785f)
                curveTo(2.7109f, 3.0552f, 3.0552f, 2.7109f, 3.4785f, 2.4952f)
                curveTo(3.9598f, 2.25f, 4.5899f, 2.25f, 5.85f, 2.25f)
                horizontalLineTo(12.15f)
                curveTo(13.4101f, 2.25f, 14.0402f, 2.25f, 14.5215f, 2.4952f)
                curveTo(14.9448f, 2.7109f, 15.289f, 3.0552f, 15.5048f, 3.4785f)
                curveTo(15.75f, 3.9598f, 15.75f, 4.5899f, 15.75f, 5.85f)
                verticalLineTo(12.15f)
                moveTo(7.875f, 6.375f)
                curveTo(7.875f, 7.2034f, 7.2034f, 7.875f, 6.375f, 7.875f)
                curveTo(5.5466f, 7.875f, 4.875f, 7.2034f, 4.875f, 6.375f)
                curveTo(4.875f, 5.5466f, 5.5466f, 4.875f, 6.375f, 4.875f)
                curveTo(7.2034f, 4.875f, 7.875f, 5.5466f, 7.875f, 6.375f)
                close()
            }
        }
        .build()
        return _icImage!!
    }

private var _icImage: ImageVector? = null
