package com.example.connex.ui.svg.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.connex.ui.svg.IconPack

public val IconPack.IcConnexUsedUser: ImageVector
    get() {
        if (_icconnexuseduser != null) {
            return _icconnexuseduser!!
        }
        _icconnexuseduser = Builder(name = "Icconnexuseduser", defaultWidth = 16.0.dp, defaultHeight
                = 16.0.dp, viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF459AFE)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(1.7417f, 8.7222f)
                curveTo(1.8219f, 8.7817f, 1.9035f, 8.8381f, 1.9864f, 8.8912f)
                curveTo(1.9225f, 8.966f, 1.8608f, 9.0435f, 1.8014f, 9.1236f)
                curveTo(0.3835f, 11.0347f, 0.7833f, 13.7334f, 2.6944f, 15.1513f)
                curveTo(4.6055f, 16.5691f, 7.3042f, 16.1693f, 8.722f, 14.2582f)
                curveTo(8.7815f, 14.178f, 8.8378f, 14.0965f, 8.8909f, 14.0137f)
                curveTo(8.9658f, 14.0777f, 9.0435f, 14.1395f, 9.1237f, 14.1991f)
                curveTo(11.0348f, 15.617f, 13.7335f, 15.2171f, 15.1514f, 13.306f)
                curveTo(16.5692f, 11.3949f, 16.1694f, 8.6963f, 14.2583f, 7.2784f)
                curveTo(14.178f, 7.2188f, 14.0963f, 7.1625f, 14.0134f, 7.1093f)
                curveTo(14.0773f, 7.0343f, 14.1392f, 6.9567f, 14.1987f, 6.8764f)
                curveTo(15.6166f, 4.9653f, 15.2168f, 2.2666f, 13.3057f, 0.8487f)
                curveTo(11.3946f, -0.5691f, 8.6959f, -0.1693f, 7.2781f, 1.7418f)
                curveTo(7.2185f, 1.8221f, 7.1621f, 1.9038f, 7.1089f, 1.9868f)
                curveTo(7.034f, 1.9229f, 6.9565f, 1.8611f, 6.8762f, 1.8016f)
                curveTo(4.9651f, 0.3837f, 2.2665f, 0.7835f, 0.8486f, 2.6946f)
                curveTo(-0.5692f, 4.6057f, -0.1694f, 7.3044f, 1.7417f, 8.7222f)
                close()
            }
        }
        .build()
        return _icconnexuseduser!!
    }

private var _icconnexuseduser: ImageVector? = null
