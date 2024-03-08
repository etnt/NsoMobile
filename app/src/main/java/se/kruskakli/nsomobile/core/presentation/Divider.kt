package se.kruskakli.nsomobile.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Divider() {
    Spacer(modifier = Modifier
        .height(1.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
    )
}