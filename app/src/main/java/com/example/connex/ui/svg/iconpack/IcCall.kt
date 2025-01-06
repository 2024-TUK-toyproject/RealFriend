package com.example.connex.ui.svg.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.connex.ui.svg.IconPack

public val IconPack.IcCall: ImageVector
    get() {
        if (_icCall != null) {
            return _icCall!!
        }
        _icCall = Builder(name = "IcCall", defaultWidth = 23.0.dp, defaultHeight = 23.0.dp,
                viewportWidth = 23.0f, viewportHeight = 23.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF23B169)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(6.4118f, 16.5882f)
                    curveTo(11.0975f, 21.2739f, 14.6516f, 22.3405f, 17.4829f, 21.6666f)
                    curveTo(19.0549f, 21.2923f, 20.4562f, 20.0117f, 21.3923f, 18.9463f)
                    curveTo(22.1018f, 18.1378f, 21.9679f, 16.8947f, 21.1169f, 16.2363f)
                    lineTo(17.3736f, 13.3394f)
                    curveTo(16.9999f, 13.0502f, 16.4691f, 13.0836f, 16.1345f, 13.4182f)
                    lineTo(14.1467f, 15.406f)
                    curveTo(13.8397f, 15.713f, 13.3664f, 15.774f, 12.9967f, 15.5469f)
                    curveTo(12.3579f, 15.1547f, 11.2861f, 14.3946f, 9.9463f, 13.0542f)
                    curveTo(8.6066f, 11.7145f, 7.8464f, 10.6427f, 7.4537f, 10.0039f)
                    curveTo(7.2266f, 9.6341f, 7.2875f, 9.1609f, 7.5946f, 8.8539f)
                    lineTo(9.5824f, 6.8661f)
                    curveTo(9.9164f, 6.532f, 9.9504f, 6.0013f, 9.6611f, 5.627f)
                    lineTo(6.7643f, 1.8837f)
                    curveTo(6.1059f, 1.0327f, 4.8622f, 0.8987f, 4.0543f, 1.6083f)
                    curveTo(2.9888f, 2.5438f, 1.7083f, 3.9451f, 1.334f, 5.5177f)
                    curveTo(0.6601f, 8.3496f, 1.7267f, 11.9031f, 6.4124f, 16.5888f)
                    lineTo(6.4118f, 16.5882f)
                    close()
                }
            }
        }
        .build()
        return _icCall!!
    }

private var _icCall: ImageVector? = null
