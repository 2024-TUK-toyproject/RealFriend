import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


@Composable
fun rememberConnexLogo2(): ImageVector {
    return remember {
        ImageVector.Builder(
                name = "Union",
                defaultWidth = 32.dp,
                defaultHeight = 32.dp,
                viewportWidth = 32f,
                viewportHeight = 32f
            ).apply {
				group {
					path(
    					fill = Brush.radialGradient(
		center = Offset(0f, 0f),
		radius = 1f,
		colorStops = arrayOf(
				0f to Color(0xFF57AFFF),
				1f to Color(0xFFFFFFFF),
			),
		),
    					fillAlpha = 1.0f,
    					stroke = null,
    					strokeAlpha = 1.0f,
    					strokeLineWidth = 1.0f,
    					strokeLineCap = StrokeCap.Butt,
    					strokeLineJoin = StrokeJoin.Miter,
    					strokeLineMiter = 1.0f,
    					pathFillType = PathFillType.EvenOdd
					) {
						moveTo(0f, 5.86344f)
						curveTo(0f, 2.6252f, 2.6252f, 0f, 5.8634f, 0f)
						horizontalLineTo(10.1265f)
						curveTo(13.3648f, 0f, 15.99f, 2.6252f, 15.99f, 5.8634f)
						verticalLineTo(8.21906f)
						curveTo(15.99f, 8.2246f, 15.9945f, 8.2291f, 16f, 8.2291f)
						curveTo(16.0055f, 8.2291f, 16.01f, 8.2246f, 16.01f, 8.2191f)
						verticalLineTo(5.86344f)
						curveTo(16.01f, 2.6252f, 18.6352f, 0f, 21.8735f, 0f)
						horizontalLineTo(26.1366f)
						curveTo(29.3748f, 0f, 32f, 2.6252f, 32f, 5.8634f)
						verticalLineTo(10.1167f)
						curveTo(32f, 13.355f, 29.3748f, 15.9802f, 26.1366f, 15.9802f)
						horizontalLineTo(23.7839f)
						curveTo(23.773f, 15.9802f, 23.7641f, 15.989f, 23.7641f, 16f)
						curveTo(23.7641f, 16.011f, 23.773f, 16.0198f, 23.7839f, 16.0198f)
						horizontalLineTo(26.1157f)
						curveTo(29.354f, 16.0198f, 31.9792f, 18.645f, 31.9792f, 21.8833f)
						verticalLineTo(26.1366f)
						curveTo(31.9792f, 29.3748f, 29.354f, 32f, 26.1157f, 32f)
						horizontalLineTo(21.8526f)
						curveTo(18.6334f, 32f, 16.02f, 29.4056f, 15.9895f, 26.1935f)
						curveTo(15.9484f, 29.3964f, 13.3392f, 31.9802f, 10.1265f, 31.9802f)
						horizontalLineTo(5.86344f)
						curveTo(2.6252f, 31.9802f, 0f, 29.3551f, 0f, 26.1168f)
						verticalLineTo(21.8635f)
						curveTo(0f, 18.6252f, 2.6252f, 16f, 5.8634f, 16f)
						horizontalLineTo(7.76418f)
						curveTo(7.7697f, 16f, 7.7741f, 15.9956f, 7.7741f, 15.9901f)
						curveTo(7.7741f, 15.9846f, 7.7697f, 15.9802f, 7.7642f, 15.9802f)
						horizontalLineTo(5.86345f)
						curveTo(2.6252f, 15.9802f, 0f, 13.355f, 0f, 10.1167f)
						verticalLineTo(5.86344f)
						close()
}
					path(
    					fill = Brush.radialGradient(
		center = Offset(0f, 0f),
		radius = 1f,
		colorStops = arrayOf(
				0f to Color(0xFF6739EB),
				1f to Color(0xFFFFFFFF),
			),
		),
    					fillAlpha = 1.0f,
    					stroke = null,
    					strokeAlpha = 1.0f,
    					strokeLineWidth = 1.0f,
    					strokeLineCap = StrokeCap.Butt,
    					strokeLineJoin = StrokeJoin.Miter,
    					strokeLineMiter = 1.0f,
    					pathFillType = PathFillType.EvenOdd
					) {
						moveTo(0f, 5.86344f)
						curveTo(0f, 2.6252f, 2.6252f, 0f, 5.8634f, 0f)
						horizontalLineTo(10.1265f)
						curveTo(13.3648f, 0f, 15.99f, 2.6252f, 15.99f, 5.8634f)
						verticalLineTo(8.21906f)
						curveTo(15.99f, 8.2246f, 15.9945f, 8.2291f, 16f, 8.2291f)
						curveTo(16.0055f, 8.2291f, 16.01f, 8.2246f, 16.01f, 8.2191f)
						verticalLineTo(5.86344f)
						curveTo(16.01f, 2.6252f, 18.6352f, 0f, 21.8735f, 0f)
						horizontalLineTo(26.1366f)
						curveTo(29.3748f, 0f, 32f, 2.6252f, 32f, 5.8634f)
						verticalLineTo(10.1167f)
						curveTo(32f, 13.355f, 29.3748f, 15.9802f, 26.1366f, 15.9802f)
						horizontalLineTo(23.7839f)
						curveTo(23.773f, 15.9802f, 23.7641f, 15.989f, 23.7641f, 16f)
						curveTo(23.7641f, 16.011f, 23.773f, 16.0198f, 23.7839f, 16.0198f)
						horizontalLineTo(26.1157f)
						curveTo(29.354f, 16.0198f, 31.9792f, 18.645f, 31.9792f, 21.8833f)
						verticalLineTo(26.1366f)
						curveTo(31.9792f, 29.3748f, 29.354f, 32f, 26.1157f, 32f)
						horizontalLineTo(21.8526f)
						curveTo(18.6334f, 32f, 16.02f, 29.4056f, 15.9895f, 26.1935f)
						curveTo(15.9484f, 29.3964f, 13.3392f, 31.9802f, 10.1265f, 31.9802f)
						horizontalLineTo(5.86344f)
						curveTo(2.6252f, 31.9802f, 0f, 29.3551f, 0f, 26.1168f)
						verticalLineTo(21.8635f)
						curveTo(0f, 18.6252f, 2.6252f, 16f, 5.8634f, 16f)
						horizontalLineTo(7.76418f)
						curveTo(7.7697f, 16f, 7.7741f, 15.9956f, 7.7741f, 15.9901f)
						curveTo(7.7741f, 15.9846f, 7.7697f, 15.9802f, 7.7642f, 15.9802f)
						horizontalLineTo(5.86345f)
						curveTo(2.6252f, 15.9802f, 0f, 13.355f, 0f, 10.1167f)
						verticalLineTo(5.86344f)
						close()
}
					path(
    					fill = Brush.radialGradient(
		center = Offset(0f, 0f),
		radius = 1f,
		colorStops = arrayOf(
				0f to Color(0xFF365FF1),
				1f to Color(0xFFFFFFFF),
			),
		),
    					fillAlpha = 1.0f,
    					stroke = null,
    					strokeAlpha = 1.0f,
    					strokeLineWidth = 1.0f,
    					strokeLineCap = StrokeCap.Butt,
    					strokeLineJoin = StrokeJoin.Miter,
    					strokeLineMiter = 1.0f,
    					pathFillType = PathFillType.EvenOdd
					) {
						moveTo(0f, 5.86344f)
						curveTo(0f, 2.6252f, 2.6252f, 0f, 5.8634f, 0f)
						horizontalLineTo(10.1265f)
						curveTo(13.3648f, 0f, 15.99f, 2.6252f, 15.99f, 5.8634f)
						verticalLineTo(8.21906f)
						curveTo(15.99f, 8.2246f, 15.9945f, 8.2291f, 16f, 8.2291f)
						curveTo(16.0055f, 8.2291f, 16.01f, 8.2246f, 16.01f, 8.2191f)
						verticalLineTo(5.86344f)
						curveTo(16.01f, 2.6252f, 18.6352f, 0f, 21.8735f, 0f)
						horizontalLineTo(26.1366f)
						curveTo(29.3748f, 0f, 32f, 2.6252f, 32f, 5.8634f)
						verticalLineTo(10.1167f)
						curveTo(32f, 13.355f, 29.3748f, 15.9802f, 26.1366f, 15.9802f)
						horizontalLineTo(23.7839f)
						curveTo(23.773f, 15.9802f, 23.7641f, 15.989f, 23.7641f, 16f)
						curveTo(23.7641f, 16.011f, 23.773f, 16.0198f, 23.7839f, 16.0198f)
						horizontalLineTo(26.1157f)
						curveTo(29.354f, 16.0198f, 31.9792f, 18.645f, 31.9792f, 21.8833f)
						verticalLineTo(26.1366f)
						curveTo(31.9792f, 29.3748f, 29.354f, 32f, 26.1157f, 32f)
						horizontalLineTo(21.8526f)
						curveTo(18.6334f, 32f, 16.02f, 29.4056f, 15.9895f, 26.1935f)
						curveTo(15.9484f, 29.3964f, 13.3392f, 31.9802f, 10.1265f, 31.9802f)
						horizontalLineTo(5.86344f)
						curveTo(2.6252f, 31.9802f, 0f, 29.3551f, 0f, 26.1168f)
						verticalLineTo(21.8635f)
						curveTo(0f, 18.6252f, 2.6252f, 16f, 5.8634f, 16f)
						horizontalLineTo(7.76418f)
						curveTo(7.7697f, 16f, 7.7741f, 15.9956f, 7.7741f, 15.9901f)
						curveTo(7.7741f, 15.9846f, 7.7697f, 15.9802f, 7.7642f, 15.9802f)
						horizontalLineTo(5.86345f)
						curveTo(2.6252f, 15.9802f, 0f, 13.355f, 0f, 10.1167f)
						verticalLineTo(5.86344f)
						close()
}
}
}.build()
    }
}

