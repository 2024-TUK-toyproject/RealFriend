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

public val IconPack.IcLoudspeaker: ImageVector
    get() {
        if (_icloudspeaker != null) {
            return _icloudspeaker!!
        }
        _icloudspeaker = Builder(name = "Icloudspeaker", defaultWidth = 512.0.dp, defaultHeight =
                512.0.dp, viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF212121)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(26.18f, 19.61f)
                arcToRelative(3.72f, 3.72f, 0.0f, true, false, 0.0f, -7.44f)
                arcToRelative(3.72f, 3.72f, 0.0f, false, false, 0.0f, 7.44f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD3D3D3)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveToRelative(11.0f, 11.0f)
                lineToRelative(0.68f, -1.0f)
                curveToRelative(1.25f, 0.0f, 2.49f, -0.29f, 3.61f, -0.83f)
                lineToRelative(6.26f, -3.08f)
                verticalLineToRelative(19.62f)
                lineToRelative(-6.26f, -3.08f)
                arcToRelative(8.209f, 8.209f, 0.0f, false, false, -3.61f, -0.84f)
                lineTo(11.0f, 20.0f)
                verticalLineToRelative(-9.0f)
                close()
                moveTo(6.216f, 29.008f)
                horizontalLineTo(8.79f)
                curveToRelative(0.67f, 0.0f, 1.21f, -0.54f, 1.21f, -1.21f)
                verticalLineTo(19.89f)
                horizontalLineTo(5.006f)
                verticalLineToRelative(7.908f)
                curveToRelative(0.0f, 0.67f, 0.54f, 1.21f, 1.21f, 1.21f)
                close()
            }
            path(fill = SolidColor(Color(0xFFF8312F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(24.07f, 3.0f)
                arcTo(3.07f, 3.07f, 0.0f, false, false, 21.0f, 6.07f)
                verticalLineToRelative(19.65f)
                arcToRelative(3.07f, 3.07f, 0.0f, false, false, 6.14f, 0.0f)
                verticalLineTo(6.07f)
                arcTo(3.077f, 3.077f, 0.0f, false, false, 24.07f, 3.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFCA0B4A)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(3.727f, 10.0f)
                horizontalLineTo(12.0f)
                verticalLineToRelative(11.78f)
                horizontalLineTo(3.727f)
                curveTo(2.77f, 21.78f, 2.0f, 21.03f, 2.0f, 20.11f)
                verticalLineToRelative(-8.43f)
                curveToRelative(0.0f, -0.93f, 0.77f, -1.68f, 1.727f, -1.68f)
                close()
            }
        }
        .build()
        return _icloudspeaker!!
    }

private var _icloudspeaker: ImageVector? = null
