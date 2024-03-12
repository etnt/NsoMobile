package se.kruskakli.nsomobile.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle

// Build a text string with an optional annotation
@Composable
fun annotatedText(
    text: String,
    annotation: String? = null
) : AnnotatedString
{
    val annotatedText = buildAnnotatedString {
        append(text)
        if (annotation != null) {
            withStyle(
                style = SpanStyle(
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    fontStyle = FontStyle.Italic,
                )
            ) {
                append(annotation)
            }
        }
    }
    return annotatedText
}