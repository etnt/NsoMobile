package se.kruskakli.nsomobile.core.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun FieldComponentWithHelp(
    field: FieldWithHelp,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    var show by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 4.dp, end = 4.dp, bottom = 0.dp)
            .clickable { show = !show }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${field.label}:",
                color = textColor,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = field.value ?: "",
                color = textColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
        if (show) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp),
                text = field.help ?: "",
                fontStyle = FontStyle.Italic,
                color = textColor,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
