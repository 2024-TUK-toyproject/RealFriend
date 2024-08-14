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

public val IconPack.IcMenudot: ImageVector
    get() {
        if (_icmenudot != null) {
            return _icmenudot!!
        }
        _icmenudot = Builder(name = "Icmenudot", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF416BFF)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(1.3333f, 9.3335f)
                    curveTo(2.0697f, 9.3335f, 2.6667f, 8.7366f, 2.6667f, 8.0002f)
                    curveTo(2.6667f, 7.2638f, 2.0697f, 6.6669f, 1.3333f, 6.6669f)
                    curveTo(0.5969f, 6.6669f, -0.0f, 7.2638f, -0.0f, 8.0002f)
                    curveTo(-0.0f, 8.7366f, 0.5969f, 9.3335f, 1.3333f, 9.3335f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF416BFF)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(8.0f, 9.3335f)
                    curveTo(8.7364f, 9.3335f, 9.3334f, 8.7366f, 9.3334f, 8.0002f)
                    curveTo(9.3334f, 7.2638f, 8.7364f, 6.6669f, 8.0f, 6.6669f)
                    curveTo(7.2637f, 6.6669f, 6.6667f, 7.2638f, 6.6667f, 8.0002f)
                    curveTo(6.6667f, 8.7366f, 7.2637f, 9.3335f, 8.0f, 9.3335f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF416BFF)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(14.6666f, 9.3335f)
                    curveTo(15.403f, 9.3335f, 16.0f, 8.7366f, 16.0f, 8.0002f)
                    curveTo(16.0f, 7.2638f, 15.403f, 6.6669f, 14.6666f, 6.6669f)
                    curveTo(13.9302f, 6.6669f, 13.3333f, 7.2638f, 13.3333f, 8.0002f)
                    curveTo(13.3333f, 8.7366f, 13.9302f, 9.3335f, 14.6666f, 9.3335f)
                    close()
                }
            }
        }
        .build()
        return _icmenudot!!
    }

private var _icmenudot: ImageVector? = null
