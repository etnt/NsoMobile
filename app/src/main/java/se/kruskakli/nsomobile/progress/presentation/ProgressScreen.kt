package se.kruskakli.nsomobile.progress.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.progress.domain.ProgressIntent
import se.kruskakli.nsomobile.progress.domain.ProgressViewModel

@Composable
fun ProgressScreen(

) {
    val viewModel = koinViewModel<ProgressViewModel>()
    viewModel.handleIntent(ProgressIntent.showProgress)

    ProgressContent(
        viewModel
    )
}

@Composable
fun ProgressContent(
    viewModel: ProgressViewModel
) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Text(
            text = "Progress"
        )
    }
}
