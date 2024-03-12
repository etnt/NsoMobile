package se.kruskakli.nsomobile.main.presentation

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

// Taken from: https://www.composables.com/icons
@Composable
fun RememberQuestionMark(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "question_mark",
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
                moveTo(20.042f, 26.25f)
                quadToRelative(-1.042f, 0f, -1.584f, -0.708f)
                quadToRelative(-0.541f, -0.709f, -0.416f, -1.667f)
                quadToRelative(0.25f, -1.583f, 1f, -2.771f)
                quadToRelative(0.75f, -1.187f, 2.25f, -2.437f)
                quadToRelative(1.625f, -1.375f, 2.52f, -2.688f)
                quadToRelative(0.896f, -1.312f, 0.896f, -2.854f)
                quadToRelative(0f, -1.875f, -1.25f, -3.104f)
                reflectiveQuadToRelative(-3.5f, -1.229f)
                quadToRelative(-1.541f, 0f, -2.541f, 0.604f)
                reflectiveQuadToRelative(-1.625f, 1.521f)
                quadToRelative(-0.5f, 0.666f, -1.23f, 0.937f)
                quadToRelative(-0.729f, 0.271f, -1.437f, -0.021f)
                quadToRelative(-0.833f, -0.375f, -1.042f, -1.291f)
                quadToRelative(-0.208f, -0.917f, 0.292f, -1.625f)
                quadToRelative(1.208f, -1.75f, 3.125f, -2.834f)
                quadTo(17.417f, 5f, 19.958f, 5f)
                quadToRelative(4.25f, 0f, 6.521f, 2.333f)
                quadToRelative(2.271f, 2.334f, 2.271f, 5.709f)
                quadToRelative(0f, 1.958f, -0.917f, 3.666f)
                quadToRelative(-0.916f, 1.709f, -2.791f, 3.459f)
                quadToRelative(-1.459f, 1.291f, -2.021f, 2.125f)
                quadToRelative(-0.563f, 0.833f, -0.729f, 1.875f)
                quadToRelative(-0.167f, 0.958f, -0.792f, 1.521f)
                quadToRelative(-0.625f, 0.562f, -1.458f, 0.562f)
                close()
                moveToRelative(-0.084f, 10.292f)
                quadToRelative(-1.208f, 0f, -2.062f, -0.875f)
                quadToRelative(-0.854f, -0.875f, -0.854f, -2.042f)
                quadToRelative(0f, -1.25f, 0.854f, -2.083f)
                quadToRelative(0.854f, -0.834f, 2.062f, -0.834f)
                quadToRelative(1.209f, 0f, 2.063f, 0.834f)
                quadToRelative(0.854f, 0.833f, 0.854f, 2.083f)
                quadToRelative(0f, 1.167f, -0.854f, 2.042f)
                quadToRelative(-0.854f, 0.875f, -2.063f, 0.875f)
                close()
            }
        }.build()
    }
}