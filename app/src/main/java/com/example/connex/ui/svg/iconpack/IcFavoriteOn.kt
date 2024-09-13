package com.example.connex.ui.svg.iconpack

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.connex.ui.svg.IconPack

public val IconPack.IcFavoriteOn: ImageVector
    get() {
        if (_icfavoriteon != null) {
            return _icfavoriteon!!
        }
        _icfavoriteon = Builder(name = "Icfavoriteon", defaultWidth = 20.0.dp, defaultHeight =
                20.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            group {
                path(fill = linearGradient(0.0f to Color(0xFF8FFFF1), 1.0f to Color(0xFFC9FF93),
                        start = Offset(16.6645f,3.06249f), end = Offset(3.96046f,17.3419f)), stroke
                        = null, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin =
                        Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                    moveTo(1.1056f, 10.3336f)
                    lineTo(4.0723f, 12.5002f)
                    lineTo(2.9456f, 15.9894f)
                    curveTo(2.7636f, 16.5306f, 2.7613f, 17.1161f, 2.9391f, 17.6587f)
                    curveTo(3.1169f, 18.2012f, 3.4653f, 18.6718f, 3.9323f, 19.0002f)
                    curveTo(4.3913f, 19.3392f, 4.9476f, 19.5208f, 5.5183f, 19.518f)
                    curveTo(6.0889f, 19.5151f, 6.6434f, 19.328f, 7.099f, 18.9844f)
                    lineTo(9.9998f, 16.8494f)
                    lineTo(12.9015f, 18.9819f)
                    curveTo(13.3597f, 19.319f, 13.913f, 19.502f, 14.4818f, 19.5048f)
                    curveTo(15.0506f, 19.5076f, 15.6056f, 19.3299f, 16.0671f, 18.9973f)
                    curveTo(16.5286f, 18.6648f, 16.8727f, 18.1944f, 17.05f, 17.654f)
                    curveTo(17.2274f, 17.1135f, 17.2287f, 16.5307f, 17.054f, 15.9894f)
                    lineTo(15.9273f, 12.5002f)
                    lineTo(18.894f, 10.3336f)
                    curveTo(19.3516f, 9.999f, 19.6918f, 9.5284f, 19.8659f, 8.9889f)
                    curveTo(20.0401f, 8.4494f, 20.0393f, 7.8687f, 19.8637f, 7.3297f)
                    curveTo(19.6881f, 6.7907f, 19.3467f, 6.321f, 18.8882f, 5.9877f)
                    curveTo(18.4296f, 5.6543f, 17.8775f, 5.4744f, 17.3106f, 5.4736f)
                    horizontalLineTo(13.6665f)
                    lineTo(12.5606f, 2.0269f)
                    curveTo(12.3867f, 1.4844f, 12.045f, 1.0111f, 11.5848f, 0.6753f)
                    curveTo(11.1245f, 0.3395f, 10.5695f, 0.1586f, 9.9998f, 0.1586f)
                    curveTo(9.4301f, 0.1586f, 8.8751f, 0.3395f, 8.4148f, 0.6753f)
                    curveTo(7.9546f, 1.0111f, 7.6129f, 1.4844f, 7.439f, 2.0269f)
                    lineTo(6.3331f, 5.4736f)
                    horizontalLineTo(2.6923f)
                    curveTo(2.1254f, 5.4744f, 1.5733f, 5.6543f, 1.1148f, 5.9877f)
                    curveTo(0.6563f, 6.321f, 0.3148f, 6.7907f, 0.1392f, 7.3297f)
                    curveTo(-0.0364f, 7.8687f, -0.0371f, 8.4494f, 0.137f, 8.9889f)
                    curveTo(0.3112f, 9.5284f, 0.6514f, 9.999f, 1.109f, 10.3336f)
                    horizontalLineTo(1.1056f)
                    close()
                }
            }
        }
        .build()
        return _icfavoriteon!!
    }

private var _icfavoriteon: ImageVector? = null
