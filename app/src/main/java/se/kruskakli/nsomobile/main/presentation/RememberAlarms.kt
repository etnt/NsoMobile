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
fun RememberAlarms(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "alarm",
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
                moveTo(20f, 36.333f)
                quadToRelative(-3.042f, 0f, -5.729f, -1.145f)
                quadToRelative(-2.688f, -1.146f, -4.688f, -3.146f)
                quadToRelative(-2f, -2f, -3.145f, -4.667f)
                quadToRelative(-1.146f, -2.667f, -1.146f, -5.75f)
                quadToRelative(0f, -3.042f, 1.146f, -5.729f)
                quadToRelative(1.145f, -2.688f, 3.145f, -4.688f)
                quadToRelative(2f, -2f, 4.688f, -3.166f)
                quadTo(16.958f, 6.875f, 20f, 6.875f)
                quadToRelative(3.042f, 0f, 5.729f, 1.167f)
                quadToRelative(2.688f, 1.166f, 4.688f, 3.166f)
                quadToRelative(2f, 2f, 3.145f, 4.688f)
                quadToRelative(1.146f, 2.687f, 1.146f, 5.729f)
                quadToRelative(0f, 3.083f, -1.146f, 5.75f)
                quadToRelative(-1.145f, 2.667f, -3.145f, 4.667f)
                reflectiveQuadToRelative(-4.688f, 3.146f)
                quadTo(23.042f, 36.333f, 20f, 36.333f)
                close()
                moveToRelative(0f, -14.666f)
                close()
                moveToRelative(-1.208f, -6.959f)
                verticalLineToRelative(7f)
                quadToRelative(0f, 0.25f, 0.083f, 0.48f)
                quadToRelative(0.083f, 0.229f, 0.292f, 0.437f)
                lineToRelative(4.875f, 4.875f)
                quadToRelative(0.333f, 0.375f, 0.875f, 0.375f)
                quadToRelative(0.541f, 0f, 0.916f, -0.417f)
                quadToRelative(0.375f, -0.375f, 0.375f, -0.916f)
                quadToRelative(0f, -0.542f, -0.375f, -0.959f)
                lineToRelative(-4.416f, -4.375f)
                verticalLineToRelative(-6.541f)
                quadToRelative(0f, -0.542f, -0.375f, -0.917f)
                reflectiveQuadToRelative(-0.959f, -0.375f)
                quadToRelative(-0.541f, 0f, -0.916f, 0.396f)
                reflectiveQuadToRelative(-0.375f, 0.937f)
                close()
                moveToRelative(-13.584f, -3f)
                quadToRelative(-0.416f, 0.375f, -0.937f, 0.375f)
                quadToRelative(-0.521f, 0f, -0.896f, -0.375f)
                quadToRelative(-0.417f, -0.416f, -0.417f, -0.958f)
                reflectiveQuadToRelative(0.417f, -0.917f)
                lineToRelative(4.917f, -4.791f)
                quadToRelative(0.375f, -0.375f, 0.916f, -0.375f)
                quadToRelative(0.542f, 0f, 0.917f, 0.375f)
                quadToRelative(0.375f, 0.416f, 0.375f, 0.958f)
                reflectiveQuadToRelative(-0.375f, 0.917f)
                close()
                moveToRelative(29.584f, -0.041f)
                lineToRelative(-4.917f, -4.792f)
                quadTo(29.5f, 6.542f, 29.479f, 6f)
                quadToRelative(-0.021f, -0.542f, 0.396f, -0.917f)
                quadToRelative(0.375f, -0.375f, 0.917f, -0.375f)
                quadToRelative(0.541f, 0f, 0.916f, 0.334f)
                lineToRelative(4.959f, 4.833f)
                quadToRelative(0.375f, 0.375f, 0.375f, 0.896f)
                reflectiveQuadToRelative(-0.375f, 0.896f)
                quadToRelative(-0.375f, 0.375f, -0.917f, 0.375f)
                reflectiveQuadToRelative(-0.958f, -0.375f)
                close()
                moveTo(20f, 33.708f)
                quadToRelative(5.042f, 0f, 8.562f, -3.52f)
                quadToRelative(3.521f, -3.521f, 3.521f, -8.563f)
                quadToRelative(0f, -5.042f, -3.521f, -8.562f)
                quadTo(25.042f, 9.542f, 20f, 9.542f)
                reflectiveQuadToRelative(-8.562f, 3.521f)
                quadToRelative(-3.521f, 3.52f, -3.521f, 8.562f)
                reflectiveQuadToRelative(3.521f, 8.563f)
                quadToRelative(3.52f, 3.52f, 8.562f, 3.52f)
                close()
            }
        }.build()
    }
}
