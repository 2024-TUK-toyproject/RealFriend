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

public val IconPack.IcSmileface: ImageVector
    get() {
        if (_icSmileface != null) {
            return _icSmileface!!
        }
        _icSmileface = Builder(name = "IcSmileface", defaultWidth = 40.0.dp, defaultHeight =
                40.0.dp, viewportWidth = 40.0f, viewportHeight = 40.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFFFFC84D)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(20.0f, 37.5f)
                    curveTo(29.665f, 37.5f, 37.5f, 29.665f, 37.5f, 20.0f)
                    curveTo(37.5f, 10.335f, 29.665f, 2.5f, 20.0f, 2.5f)
                    curveTo(10.335f, 2.5f, 2.5f, 10.335f, 2.5f, 20.0f)
                    curveTo(2.5f, 29.665f, 10.335f, 37.5f, 20.0f, 37.5f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(15.285f, 17.954f)
                    curveTo(16.4415f, 17.954f, 17.379f, 16.7326f, 17.379f, 15.226f)
                    curveTo(17.379f, 13.7194f, 16.4415f, 12.498f, 15.285f, 12.498f)
                    curveTo(14.1286f, 12.498f, 13.191f, 13.7194f, 13.191f, 15.226f)
                    curveTo(13.191f, 16.7326f, 14.1286f, 17.954f, 15.285f, 17.954f)
                    close()
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(24.7151f, 17.954f)
                    curveTo(25.8716f, 17.954f, 26.8091f, 16.7326f, 26.8091f, 15.226f)
                    curveTo(26.8091f, 13.7194f, 25.8716f, 12.498f, 24.7151f, 12.498f)
                    curveTo(23.5586f, 12.498f, 22.6211f, 13.7194f, 22.6211f, 15.226f)
                    curveTo(22.6211f, 16.7326f, 23.5586f, 17.954f, 24.7151f, 17.954f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFFF6D00)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(29.555f, 21.411f)
                    curveTo(30.288f, 21.411f, 30.88f, 22.067f, 30.745f, 22.787f)
                    curveTo(29.942f, 27.085f, 25.437f, 30.376f, 20.0f, 30.376f)
                    curveTo(14.563f, 30.376f, 10.058f, 27.085f, 9.255f, 22.787f)
                    curveTo(9.12f, 22.067f, 9.713f, 21.411f, 10.445f, 21.411f)
                    horizontalLineTo(29.555f)
                    close()
                }
                path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(19.9999f, 23.944f)
                    curveTo(23.5649f, 23.944f, 26.8639f, 23.004f, 29.5549f, 21.411f)
                    horizontalLineTo(10.4449f)
                    curveTo(13.1359f, 23.004f, 16.4349f, 23.944f, 19.9999f, 23.944f)
                    close()
                }
            }
        }
        .build()
        return _icSmileface!!
    }

private var _icSmileface: ImageVector? = null
