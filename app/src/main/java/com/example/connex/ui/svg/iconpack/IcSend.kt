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

public val IconPack.IcSend: ImageVector
    get() {
        if (_icSend != null) {
            return _icSend!!
        }
        _icSend = Builder(name = "IcSend", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF9B9C9F)),
                    strokeLineWidth = 1.66667f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(8.7518f, 10.0003f)
                horizontalLineTo(4.1685f)
                moveTo(4.0979f, 10.2432f)
                lineTo(2.1522f, 16.0555f)
                curveTo(1.9993f, 16.5121f, 1.9229f, 16.7404f, 1.9777f, 16.881f)
                curveTo(2.0254f, 17.0031f, 2.1277f, 17.0957f, 2.2539f, 17.1309f)
                curveTo(2.3993f, 17.1715f, 2.6188f, 17.0727f, 3.0579f, 16.8751f)
                lineTo(16.9842f, 10.6083f)
                curveTo(17.4128f, 10.4154f, 17.6271f, 10.319f, 17.6933f, 10.185f)
                curveTo(17.7508f, 10.0686f, 17.7508f, 9.932f, 17.6933f, 9.8156f)
                curveTo(17.6271f, 9.6817f, 17.4128f, 9.5852f, 16.9842f, 9.3924f)
                lineTo(3.0531f, 3.1234f)
                curveTo(2.6153f, 2.9264f, 2.3964f, 2.8279f, 2.2512f, 2.8683f)
                curveTo(2.1251f, 2.9034f, 2.0228f, 2.9957f, 1.975f, 3.1176f)
                curveTo(1.9199f, 3.2579f, 1.9956f, 3.4857f, 2.1468f, 3.9414f)
                lineTo(4.0985f, 9.8216f)
                curveTo(4.1245f, 9.8998f, 4.1374f, 9.939f, 4.1426f, 9.979f)
                curveTo(4.1471f, 10.0145f, 4.1471f, 10.0504f, 4.1424f, 10.0859f)
                curveTo(4.1372f, 10.1259f, 4.1241f, 10.165f, 4.0979f, 10.2432f)
                close()
            }
        }
        .build()
        return _icSend!!
    }

private var _icSend: ImageVector? = null
