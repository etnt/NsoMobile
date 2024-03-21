package se.kruskakli.nsomobile.progress.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.core.presentation.CenteredProgressIndicator
import se.kruskakli.nsomobile.progress.domain.ProgressIntent
import se.kruskakli.nsomobile.progress.domain.ProgressUi
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
    val progressTree by viewModel.progressTree.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        progressTree.DisplayResult(
            onLoading = {
                CenteredProgressIndicator()
            },
            onSuccess = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    progressTree.getSuccesData().forEach { (name, rootEvents) ->
                        DisplayOperationDetails(viewModel, rootEvents)
                    }
                }
            },
            onFailure = {
                CenteredProgressIndicator()
            }
        )
    }
}


@Composable
fun DisplayOperationDetails(
    viewModel: ProgressViewModel,
    rootEvents: List<ProgressUi.ProgressEvent>,
    level: Int = 0
) {
    for (event in rootEvents) {
        // Determine the padding for hierarchical display
        val padding = " ".repeat(level * 4)

        // Calculate the elapsed time if this event has children
        val elapsedTime = if (event.children.isNotEmpty()) {
            val start = viewModel.parseTimestamp(event.timestamp)
            val end = viewModel.parseTimestamp(event.children.maxOf { it.timestamp })
            viewModel.calculateElapsedTime(start, end)
        } else {
            0.0 // No children means no elapsed time
        }

        // Display the current event's details
        Text(
            "$padding- ${event.message} [Elapsed Time: $elapsedTime s] span-id(${event.spanId}) trace-id(${event.traceId}) transaction-id(${event.transactionId})"
        )

        // Recursively display child events, if any
        DisplayOperationDetails(viewModel, event.children, level + 1)
    }
}
