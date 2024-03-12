package se.kruskakli.nsomobile.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InsideCardWithHelp(
    header: String,
    fields: List<FieldWithHelp?>,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    color: Color = MaterialTheme.colorScheme.surface,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 2.dp, bottom = 2.dp, end = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Text(
                text = header,
                color = textColor,
                style = MaterialTheme.typography.titleSmall
            )
            fields.forEach { field ->
                field?.let {
                    FieldComponentWithHelp(
                        field = it,
                        textColor = textColor
                    )
                }
            }
        }
    }
}