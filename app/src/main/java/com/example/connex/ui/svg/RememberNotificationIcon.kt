import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


@Composable
fun rememberNotificationIcon(): ImageVector {
    return remember {
        ImageVector.Builder(
                name = "FiRrBell",
                defaultWidth = 20.dp,
                defaultHeight = 20.dp,
                viewportWidth = 20f,
                viewportHeight = 20f
            ).apply {
				group {
					path(
    					fill = SolidColor(Color(0xFF1C1B1F)),
    					fillAlpha = 1.0f,
    					stroke = null,
    					strokeAlpha = 1.0f,
    					strokeLineWidth = 1.0f,
    					strokeLineCap = StrokeCap.Butt,
    					strokeLineJoin = StrokeJoin.Miter,
    					strokeLineMiter = 1.0f,
    					pathFillType = PathFillType.NonZero
					) {
						moveTo(18.7956f, 11.3851f)
						lineTo(17.2122f, 5.6884f)
						curveTo(16.7481f, 4.0195f, 15.7395f, 2.5536f, 14.3467f, 1.5238f)
						curveTo(12.9539f, 0.4939f, 11.2567f, -0.0408f, 9.5251f, 0.0046f)
						curveTo(7.7935f, 0.05f, 6.1268f, 0.6729f, 4.7898f, 1.7744f)
						curveTo(3.4528f, 2.8758f, 2.5224f, 4.3925f, 2.1464f, 6.0834f)
						lineTo(0.920574f, 11.5959f)
						curveTo(0.7852f, 12.205f, 0.7883f, 12.8368f, 0.9297f, 13.4445f)
						curveTo(1.0712f, 14.0522f, 1.3473f, 14.6205f, 1.7377f, 15.1072f)
						curveTo(2.1282f, 15.5939f, 2.6229f, 15.9868f, 3.1855f, 16.2567f)
						curveTo(3.7481f, 16.5266f, 4.3641f, 16.6668f, 4.9881f, 16.6667f)
						horizontalLineTo(5.91641f)
						curveTo(6.1077f, 17.6086f, 6.6187f, 18.4555f, 7.3629f, 19.0637f)
						curveTo(8.107f, 19.672f, 9.0386f, 20.0042f, 9.9997f, 20.0042f)
						curveTo(10.9609f, 20.0042f, 11.8924f, 19.672f, 12.6366f, 19.0637f)
						curveTo(13.3808f, 18.4555f, 13.8918f, 17.6086f, 14.0831f, 16.6667f)
						horizontalLineTo(14.7814f)
						curveTo(15.4237f, 16.6668f, 16.0574f, 16.5183f, 16.6329f, 16.2329f)
						curveTo(17.2083f, 15.9476f, 17.71f, 15.533f, 18.0988f, 15.0216f)
						curveTo(18.4875f, 14.5103f, 18.7527f, 13.9159f, 18.8737f, 13.2851f)
						curveTo(18.9948f, 12.6543f, 18.9683f, 12.004f, 18.7964f, 11.3851f)
						horizontalLineTo(18.7956f)
						close()
						moveTo(9.99974f, 18.3334f)
						curveTo(9.4845f, 18.3313f, 8.9825f, 18.17f, 8.5625f, 17.8717f)
						curveTo(8.1424f, 17.5733f, 7.8248f, 17.1525f, 7.6531f, 16.6667f)
						horizontalLineTo(12.3464f)
						curveTo(12.1747f, 17.1525f, 11.857f, 17.5733f, 11.437f, 17.8717f)
						curveTo(11.0169f, 18.17f, 10.515f, 18.3313f, 9.9997f, 18.3334f)
						close()
						moveTo(16.7714f, 14.0126f)
						curveTo(16.5392f, 14.3206f, 16.2383f, 14.5703f, 15.8927f, 14.7417f)
						curveTo(15.5472f, 14.9131f, 15.1663f, 15.0016f, 14.7806f, 15.0001f)
						horizontalLineTo(4.98807f)
						curveTo(4.6137f, 15f, 4.2442f, 14.9159f, 3.9067f, 14.7539f)
						curveTo(3.5692f, 14.5919f, 3.2724f, 14.3562f, 3.0382f, 14.0641f)
						curveTo(2.804f, 13.7721f, 2.6384f, 13.4312f, 2.5535f, 13.0666f)
						curveTo(2.4687f, 12.702f, 2.4668f, 12.323f, 2.5481f, 11.9576f)
						lineTo(3.77307f, 6.44423f)
						curveTo(4.0684f, 5.1161f, 4.7992f, 3.9247f, 5.8493f, 3.0596f)
						curveTo(6.8994f, 2.1945f, 8.2086f, 1.7052f, 9.5687f, 1.6696f)
						curveTo(10.9288f, 1.634f, 12.2618f, 2.054f, 13.3558f, 2.863f)
						curveTo(14.4498f, 3.6719f, 15.242f, 4.8234f, 15.6064f, 6.1342f)
						lineTo(17.1897f, 11.8309f)
						curveTo(17.2944f, 12.202f, 17.311f, 12.5925f, 17.2384f, 12.9712f)
						curveTo(17.1658f, 13.3499f, 17.0059f, 13.7064f, 16.7714f, 14.0126f)
						close()
}
}
}.build()
    }
}

