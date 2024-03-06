package se.kruskakli.nsomobile.utils.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

/*
   Prompt:
   The selected code consists of a Composable that will create an OutlinedCard.
   Create a function that can create a similar composable: OutlinedCards ,which
   takes a list of fields but also a list of OutlinedCard that should be nested
   within.
*/
@Composable
fun OutlinedCards(
    header: String,
    fields: List<Field>,
    cards: List<@Composable () -> Unit>,
    annotatedHeader: AnnotatedString? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    color: Color = MaterialTheme.colorScheme.surface,
    show: Boolean = true,
    modifier: Modifier = Modifier
) {
    var showAll by remember { mutableStateOf(show) }

    OutlinedCard(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp, top = 2.dp, bottom = 4.dp, end = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showAll = !showAll }
                .background(color)
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
        ) {
            // An optional annotated header takes precedence over the header!
            val text = annotatedHeader ?: AnnotatedString(header)
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.titleSmall
            )
            if (showAll) {
                fields.forEach { field ->
                    FieldComponent(field, textColor)
                }
                cards.forEach { card ->
                    card()
                }
            }
        }
    }
}
