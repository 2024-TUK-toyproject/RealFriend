import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


@Composable
fun rememberBottomBarHomeIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
                name = "Rememberbottombarhomeicon",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
				group {
					path(
    					fill = SolidColor(Color(0xFFDBDCDF)),
    					fillAlpha = 1.0f,
    					stroke = null,
    					strokeAlpha = 1.0f,
    					strokeLineWidth = 1.0f,
    					strokeLineCap = StrokeCap.Butt,
    					strokeLineJoin = StrokeJoin.Miter,
    					strokeLineMiter = 1.0f,
    					pathFillType = PathFillType.NonZero
					) {
						moveTo(12f, 14.9922f)
						curveTo(10.3432f, 14.9922f, 9f, 16.3353f, 9f, 17.9922f)
						verticalLineTo(23.9922f)
						horizontalLineTo(15f)
						verticalLineTo(17.9922f)
						curveTo(15f, 16.3353f, 13.6568f, 14.9922f, 12f, 14.9922f)
						close()
}
					path(
    					fill = SolidColor(Color(0xFFDBDCDF)),
    					fillAlpha = 1.0f,
    					stroke = null,
    					strokeAlpha = 1.0f,
    					strokeLineWidth = 1.0f,
    					strokeLineCap = StrokeCap.Butt,
    					strokeLineJoin = StrokeJoin.Miter,
    					strokeLineMiter = 1.0f,
    					pathFillType = PathFillType.NonZero
					) {
						moveTo(17f, 17.9929f)
						verticalLineTo(23.9929f)
						horizontalLineTo(21f)
						curveTo(22.6568f, 23.9929f, 24f, 22.6497f, 24f, 20.9929f)
						verticalLineTo(11.8719f)
						curveTo(24.0002f, 11.3524f, 23.7983f, 10.8532f, 23.437f, 10.4799f)
						lineTo(14.939f, 1.29285f)
						curveTo(13.4396f, -0.3295f, 10.9089f, -0.4291f, 9.2866f, 1.0703f)
						curveTo(9.2095f, 1.1416f, 9.1352f, 1.2158f, 9.064f, 1.2929f)
						lineTo(0.581016f, 10.4769f)
						curveTo(0.2087f, 10.8517f, -0.0001f, 11.3586f, 0f, 11.8869f)
						verticalLineTo(20.9929f)
						curveTo(0f, 22.6497f, 1.3432f, 23.9929f, 3f, 23.9929f)
						horizontalLineTo(6.99998f)
						verticalLineTo(17.9929f)
						curveTo(7.0187f, 15.2661f, 9.2203f, 13.0393f, 11.8784f, 12.9752f)
						curveTo(14.6255f, 12.9089f, 16.9791f, 15.1736f, 17f, 17.9929f)
						close()
}
					path(
    					fill = SolidColor(Color(0xFFDBDCDF)),
    					fillAlpha = 1.0f,
    					stroke = null,
    					strokeAlpha = 1.0f,
    					strokeLineWidth = 1.0f,
    					strokeLineCap = StrokeCap.Butt,
    					strokeLineJoin = StrokeJoin.Miter,
    					strokeLineMiter = 1.0f,
    					pathFillType = PathFillType.NonZero
					) {
						moveTo(12f, 14.9922f)
						curveTo(10.3432f, 14.9922f, 9f, 16.3353f, 9f, 17.9922f)
						verticalLineTo(23.9922f)
						horizontalLineTo(15f)
						verticalLineTo(17.9922f)
						curveTo(15f, 16.3353f, 13.6568f, 14.9922f, 12f, 14.9922f)
						close()
}
}
}.build()
    }
}

