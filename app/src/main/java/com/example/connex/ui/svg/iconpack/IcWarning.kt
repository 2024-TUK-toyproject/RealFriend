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

public val IconPack.IcWarning: ImageVector
    get() {
        if (_icwarning != null) {
            return _icwarning!!
        }
        _icwarning = Builder(name = "Icwarning", defaultWidth = 28.0.dp, defaultHeight = 28.0.dp,
                viewportWidth = 28.0f, viewportHeight = 28.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFFFC4D4D)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(14.0f, 28.0f)
                    curveTo(21.732f, 28.0f, 28.0f, 21.732f, 28.0f, 14.0f)
                    curveTo(28.0f, 6.268f, 21.732f, 0.0f, 14.0f, 0.0f)
                    curveTo(6.268f, 0.0f, 0.0f, 6.268f, 0.0f, 14.0f)
                    curveTo(0.0084f, 21.7285f, 6.2715f, 27.9916f, 14.0f, 28.0f)
                    close()
                    moveTo(12.8334f, 7.0f)
                    curveTo(12.8334f, 6.3557f, 13.3557f, 5.8334f, 14.0f, 5.8334f)
                    curveTo(14.6443f, 5.8334f, 15.1666f, 6.3557f, 15.1666f, 7.0f)
                    verticalLineTo(16.3334f)
                    curveTo(15.1666f, 16.9777f, 14.6443f, 17.5f, 14.0f, 17.5f)
                    curveTo(13.3557f, 17.5f, 12.8334f, 16.9777f, 12.8334f, 16.3334f)
                    verticalLineTo(7.0f)
                    close()
                    moveTo(14.0f, 21.0f)
                    curveTo(14.6443f, 21.0f, 15.1666f, 21.5223f, 15.1666f, 22.1667f)
                    curveTo(15.1666f, 22.811f, 14.6443f, 23.3333f, 14.0f, 23.3333f)
                    curveTo(13.3557f, 23.3333f, 12.8334f, 22.811f, 12.8334f, 22.1667f)
                    curveTo(12.8334f, 21.5223f, 13.3557f, 21.0f, 14.0f, 21.0f)
                    close()
                }
            }
        }
        .build()
        return _icwarning!!
    }

private var _icwarning: ImageVector? = null
