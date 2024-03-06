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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.releasenote.data.TextPiece
import se.kruskakli.nsomobile.releasenote.domain.ReleaseNoteViewModel
import se.kruskakli.nsomobile.core.presentation.OutlinedCards
import se.kruskakli.nsomobile.releasenote.data.ReleaseNote


@Composable
fun ReleaseNoteScreen(

) {
    val viewModel = koinViewModel<ReleaseNoteViewModel>()
    val releaseNotes by viewModel.releaseNotes.collectAsState()
    ReleaseNoteContent(releaseNotes)
}

@Composable
fun ReleaseNoteContent(
    releaseNotes: List<ReleaseNote>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        releaseNotes.forEach { releaseNote ->
            val cards: List<@Composable () -> Unit> = listOf(
                {
                    TextDisplay(releaseNote.textPieces)
                }
            )
            val annotatedText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append("Release: ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    append(releaseNote.version)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append(" Date: ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                is TextPiece.BulletList -> {
                    textPiece.items.forEach { item ->
                        Text(
                            text = "\u2022 ${item}",
                            style = MaterialTheme.typography.bodyMedium,
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

@Preview
@Composable
fun ReleaseNoteScreenPreview() {
    val releaseNotes = listOf(
        ReleaseNote(
            version = "0.2.0",
            date = "2024-02-17",
            textPieces = listOf(
                TextPiece.Paragraph("This is the first real release of NSO Mobile. It is still very much a work in progress, but it is now at a point where it can be useful for some people. It contains the following features:"),
                TextPiece.BulletList(
                    items = listOf(
                        "View alarms",
                        "View devices",
                        "View packages",
                        "View users",
                        "View groups",
                        "View logs",
                        "View settings",
                        "View about"
                    )
                )
            )
        )
    )
    ReleaseNoteContent(releaseNotes)
}
