package se.kruskakli.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun RememberDevices(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "devices",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.083f, 29.125f)
                verticalLineTo(9.542f)
                quadToRelative(0f, -1.084f, 0.792f, -1.875f)
                quadToRelative(0.792f, -0.792f, 1.833f, -0.792f)
                horizontalLineToRelative(23.959f)
                quadToRelative(0.541f, 0f, 0.916f, 0.396f)
                reflectiveQuadToRelative(0.375f, 0.937f)
                quadToRelative(0f, 0.542f, -0.375f, 0.938f)
                quadToRelative(-0.375f, 0.396f, -0.916f, 0.396f)
                horizontalLineTo(9.708f)
                verticalLineToRelative(19.583f)
                horizontalLineToRelative(7.709f)
                quadToRelative(0.833f, 0f, 1.395f, 0.563f)
                quadToRelative(0.563f, 0.562f, 0.563f, 1.395f)
                quadToRelative(0f, 0.834f, -0.563f, 1.417f)
                quadToRelative(-0.562f, 0.583f, -1.395f, 0.583f)
                horizontalLineTo(5.583f)
                quadToRelative(-0.791f, 0f, -1.375f, -0.583f)
                quadToRelative(-0.583f, -0.583f, -0.583f, -1.417f)
                quadToRelative(0f, -0.833f, 0.583f, -1.395f)
                quadToRelative(0.584f, -0.563f, 1.375f, -0.563f)
                close()
                moveToRelative(16.792f, 3.958f)
                quadToRelative(-0.667f, 0f, -1.125f, -0.5f)
                quadToRelative(-0.458f, -0.5f, -0.458f, -1.25f)
                verticalLineTo(13.958f)
                quadToRelative(0f, -0.625f, 0.458f, -1.104f)
                quadToRelative(0.458f, -0.479f, 1.125f, -0.479f)
                horizontalLineToRelative(10.667f)
                quadToRelative(0.75f, 0f, 1.291f, 0.479f)
                quadToRelative(0.542f, 0.479f, 0.542f, 1.104f)
                verticalLineToRelative(17.375f)
                quadToRelative(0f, 0.75f, -0.542f, 1.25f)
                quadToRelative(-0.541f, 0.5f, -1.291f, 0.5f)
                close()
                moveToRelative(1.083f, -3.958f)
                horizontalLineToRelative(8.792f)
                verticalLineTo(15f)
                horizontalLineToRelative(-8.792f)
                close()
            }
        }.build()
    }
}
