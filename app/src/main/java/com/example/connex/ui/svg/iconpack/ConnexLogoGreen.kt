package com.example.connex.ui.svg.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.connex.ui.svg.IconPack

public val IconPack.ConnexLogoGreen: ImageVector
    get() {
        if (_connexlogogreen != null) {
            return _connexlogogreen!!
        }
        _connexlogogreen = Builder(name = "Connexlogogreen", defaultWidth = 44.0.dp, defaultHeight =
                44.0.dp, viewportWidth = 44.0f, viewportHeight = 44.0f).apply {
            path(fill = SolidColor(Color(0xFFAFFD7F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(4.706f, 23.508f)
                curveTo(4.9222f, 23.6685f, 5.1421f, 23.8202f, 5.3654f, 23.9634f)
                curveTo(5.1934f, 24.1651f, 5.0271f, 24.3739f, 4.8668f, 24.5899f)
                curveTo(1.0453f, 29.7408f, 2.123f, 37.0142f, 7.2738f, 40.8357f)
                curveTo(12.4246f, 44.6572f, 19.6981f, 43.5795f, 23.5196f, 38.4287f)
                curveTo(23.6799f, 38.2127f, 23.8315f, 37.9929f, 23.9746f, 37.7698f)
                curveTo(24.1766f, 37.9422f, 24.3859f, 38.1088f, 24.6023f, 38.2694f)
                curveTo(29.7531f, 42.0909f, 37.0266f, 41.0132f, 40.8481f, 35.8624f)
                curveTo(44.6695f, 30.7116f, 43.5919f, 23.4381f, 38.4411f, 19.6166f)
                curveTo(38.2246f, 19.456f, 38.0044f, 19.3041f, 37.7809f, 19.1608f)
                curveTo(37.9532f, 18.9587f, 38.1199f, 18.7495f, 38.2805f, 18.533f)
                curveTo(42.102f, 13.3822f, 41.0243f, 6.1087f, 35.8735f, 2.2872f)
                curveTo(30.7227f, -1.5342f, 23.4492f, -0.4566f, 19.6277f, 4.6942f)
                curveTo(19.4671f, 4.9107f, 19.3152f, 5.1309f, 19.1719f, 5.3544f)
                curveTo(18.9701f, 5.1822f, 18.761f, 5.0157f, 18.5447f, 4.8553f)
                curveTo(13.3939f, 1.0338f, 6.1204f, 2.1114f, 2.299f, 7.2623f)
                curveTo(-1.5225f, 12.4131f, -0.4449f, 19.6866f, 4.706f, 23.508f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(14.606f, 17.6929f)
                moveToRelative(-1.5484f, 0.0f)
                arcToRelative(1.5484f, 1.5484f, 0.0f, true, true, 3.0968f, 0.0f)
                arcToRelative(1.5484f, 1.5484f, 0.0f, true, true, -3.0968f, 0.0f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.54839f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(24.6712f, 16.1445f)
                lineTo(22.3486f, 17.6929f)
                lineTo(24.6712f, 19.2413f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.54839f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(16.1553f, 21.5625f)
                curveTo(17.4456f, 22.8528f, 20.6456f, 24.6593f, 23.123f, 21.5625f)
            }
        }
        .build()
        return _connexlogogreen!!
    }

private var _connexlogogreen: ImageVector? = null
