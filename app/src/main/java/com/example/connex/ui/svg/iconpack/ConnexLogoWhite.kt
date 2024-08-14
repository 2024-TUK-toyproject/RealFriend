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

public val IconPack.ConnexLogoWhite: ImageVector
    get() {
        if (_connexlogowhite != null) {
            return _connexlogowhite!!
        }
        _connexlogowhite = Builder(name = "Connexlogowhite", defaultWidth = 32.0.dp, defaultHeight =
                32.0.dp, viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(3.4834f, 17.4442f)
                curveTo(3.6438f, 17.5633f, 3.807f, 17.6759f, 3.9727f, 17.7821f)
                curveTo(3.8451f, 17.9318f, 3.7217f, 18.0867f, 3.6027f, 18.247f)
                curveTo(0.767f, 22.0692f, 1.5667f, 27.4665f, 5.3889f, 30.3023f)
                curveTo(9.2111f, 33.138f, 14.6084f, 32.3383f, 17.4441f, 28.5162f)
                curveTo(17.563f, 28.3558f, 17.6756f, 28.1928f, 17.7818f, 28.0272f)
                curveTo(17.9316f, 28.1551f, 18.0869f, 28.2788f, 18.2475f, 28.3979f)
                curveTo(22.0697f, 31.2337f, 27.467f, 30.434f, 30.3027f, 26.6118f)
                curveTo(33.1385f, 22.7896f, 32.3388f, 17.3923f, 28.5166f, 14.5566f)
                curveTo(28.356f, 14.4374f, 28.1926f, 14.3247f, 28.0267f, 14.2183f)
                curveTo(28.1546f, 14.0684f, 28.2783f, 13.9131f, 28.3975f, 13.7525f)
                curveTo(31.2332f, 9.9303f, 30.4335f, 4.533f, 26.6114f, 1.6973f)
                curveTo(22.7892f, -1.1385f, 17.3919f, -0.3388f, 14.5561f, 3.4834f)
                curveTo(14.437f, 3.644f, 14.3242f, 3.8074f, 14.2179f, 3.9732f)
                curveTo(14.0681f, 3.8455f, 13.913f, 3.7219f, 13.7525f, 3.6029f)
                curveTo(9.9303f, 0.7671f, 4.533f, 1.5668f, 1.6973f, 5.389f)
                curveTo(-1.1385f, 9.2112f, -0.3388f, 14.6085f, 3.4834f, 17.4442f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.8296f, 13.1275f)
                moveToRelative(-1.149f, 0.0f)
                arcToRelative(1.149f, 1.149f, 0.0f, true, true, 2.298f, 0.0f)
                arcToRelative(1.149f, 1.149f, 0.0f, true, true, -2.298f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.4654f, 13.0123f)
                moveToRelative(-1.149f, 0.0f)
                arcToRelative(1.149f, 1.149f, 0.0f, true, true, 2.298f, 0.0f)
                arcToRelative(1.149f, 1.149f, 0.0f, true, true, -2.298f, 0.0f)
            }
        }
        .build()
        return _connexlogowhite!!
    }

private var _connexlogowhite: ImageVector? = null
