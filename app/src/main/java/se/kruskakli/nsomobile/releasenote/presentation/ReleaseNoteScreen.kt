package se.kruskakli.nsomobile.releasenote.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
import se.kruskakli.nsomobile.releasenote.data.TextPiece
import se.kruskakli.nsomobile.releasenote.domain.ReleaseNoteViewModel
import se.kruskakli.nsomobile.utils.presentation.OutlinedCards
import javax.inject.Inject
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ReleaseNoteScreen(

) {
    val viewModel = hiltViewModel<ReleaseNoteViewModel>()
    
    val releaseNotes by viewModel.releaseNotes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Log.d("ReleaseNoteScreen", "ReleaseNotes: $releaseNotes")
        releaseNotes.forEach { releaseNote ->
            val cards: List<@Composable () -> Unit> = listOf(
                {
                    TextDisplay(releaseNote.textPieces)
                }
            )
            val annotatedText = buildAnnotatedString {
                append("Release: ")
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(releaseNote.version)
                }
                append(" Date: ")
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(releaseNote.date)
                }
            }
            OutlinedCards(
                header = "",
                fields = emptyList(),
                cards = cards,
                annotatedHeader = annotatedText,
                textColor = MaterialTheme.colorScheme.onSurface,
                color = MaterialTheme.colorScheme.surface,
                show = false
            )
        }
    }
}

@Composable
fun TextDisplay(textPieces: List<TextPiece>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        textPieces.forEach { textPiece ->
            when (textPiece) {
                is TextPiece.Paragraph -> {
                    Text(
                        text = textPiece.text,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                is TextPiece.BulletList -> {
                    textPiece.items.forEach { item ->
                        Text(
                            text = "\u2022 ${item}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .padding(start = 8.dp),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}