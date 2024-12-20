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

public val IconPack.ImageGrantedMember: ImageVector
    get() {
        if (_imagegrantedmember != null) {
            return _imagegrantedmember!!
        }
        _imagegrantedmember = Builder(name = "Imagegrantedmember", defaultWidth = 178.0.dp,
                defaultHeight = 167.0.dp, viewportWidth = 178.0f, viewportHeight = 167.0f).apply {
            path(fill = SolidColor(Color(0xFF459AFE)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.743f, 88.858f)
                curveTo(18.561f, 89.464f, 19.392f, 90.038f, 20.236f, 90.579f)
                curveTo(19.586f, 91.341f, 18.957f, 92.131f, 18.351f, 92.947f)
                curveTo(3.907f, 112.417f, 7.98f, 139.91f, 27.449f, 154.354f)
                curveTo(46.919f, 168.799f, 74.411f, 164.726f, 88.856f, 145.256f)
                curveTo(89.462f, 144.44f, 90.035f, 143.609f, 90.576f, 142.766f)
                curveTo(91.339f, 143.417f, 92.13f, 144.047f, 92.948f, 144.654f)
                curveTo(112.418f, 159.099f, 139.91f, 155.026f, 154.355f, 135.556f)
                curveTo(168.799f, 116.086f, 164.726f, 88.593f, 145.257f, 74.149f)
                curveTo(144.438f, 73.542f, 143.606f, 72.967f, 142.761f, 72.425f)
                curveTo(143.413f, 71.662f, 144.043f, 70.871f, 144.65f, 70.053f)
                curveTo(159.094f, 50.583f, 155.021f, 23.09f, 135.552f, 8.646f)
                curveTo(116.082f, -5.799f, 88.59f, -1.726f, 74.145f, 17.744f)
                curveTo(73.538f, 18.562f, 72.964f, 19.394f, 72.422f, 20.239f)
                curveTo(71.659f, 19.588f, 70.869f, 18.959f, 70.052f, 18.352f)
                curveTo(50.583f, 3.908f, 23.09f, 7.981f, 8.645f, 27.451f)
                curveTo(-5.799f, 46.92f, -1.726f, 74.413f, 17.743f, 88.858f)
                close()
            }
            path(fill = null, stroke = null, strokeLineWidth = 0.0f, strokeLineCap = Butt,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(79.0f, 68.0f)
                horizontalLineToRelative(99.0f)
                verticalLineToRelative(99.0f)
                horizontalLineToRelative(-99.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(40.0f, 52.0f)
                arcToRelative(8.5f, 10.0f, 0.0f, true, false, 17.0f, 0.0f)
                arcToRelative(8.5f, 10.0f, 0.0f, true, false, -17.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(74.0f, 52.0f)
                arcToRelative(8.5f, 10.0f, 0.0f, true, false, 17.0f, 0.0f)
                arcToRelative(8.5f, 10.0f, 0.0f, true, false, -17.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(44.5f, 50.5f)
                moveToRelative(-2.5f, 0.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, true, true, 5.0f, 0.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, true, true, -5.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(77.5f, 50.5f)
                moveToRelative(-2.5f, 0.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, true, true, 5.0f, 0.0f)
                arcToRelative(2.5f, 2.5f, 0.0f, true, true, -5.0f, 0.0f)
            }
        }
        .build()
        return _imagegrantedmember!!
    }

private var _imagegrantedmember: ImageVector? = null
