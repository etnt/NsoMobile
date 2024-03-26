package se.kruskakli.nsomobile.progress.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.Divider
import se.kruskakli.nsomobile.core.presentation.CenteredProgressIndicator
import se.kruskakli.nsomobile.core.presentation.OnFailureMessageBox
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val flattenedList = progressTree.getSuccesData().values.flatten()
                    items(flattenedList) {  rootEvent->
                        DisplayOperationDetails(viewModel, listOf(rootEvent))
                    }
                }
            },
            onFailure = {
                OnFailureMessageBox(it)
            }
        )
    }
}


@Composable
fun DisplayOperationDetails(
    viewModel: ProgressViewModel,
    rootEvents: List<ProgressUi.ProgressEvent>,
    level: Int = 1
) {
    for (event in rootEvents) {
        // Determine the padding for hierarchical display
        //val padding = " ".repeat(level * 4)
        val padding = level * 8

        // Calculate the elapsed time if this event has children
        /*
        val elapsedTime = if (event.children.isNotEmpty()) {
            val start = viewModel.parseTimestamp(event.timestamp)
            val end = viewModel.parseTimestamp(event.children.maxOf { it.timestamp })
            viewModel.calculateElapsedTime(start, end)
        } else {
            0.0 // No children means no elapsed time
        }
         */

        if (event.duration != "") {
            if (level == 1) {
                Divider()
            }
            //DisplayEvent(event, padding, elapsedTime)
            DisplayEvent(event, padding)
        }

        // Recursively display child events, if any
        DisplayOperationDetails(viewModel, event.children, level + 1)
    }
}

@Composable
fun DisplayEvent(
    event: ProgressUi.ProgressEvent,
    padding: Int,
    elapsedTime: Double = 0.0
) {
    var showEventInfo by remember { mutableStateOf(false) }

    val atext = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append("${event.context}: ")
        }
        withStyle(
            style = SpanStyle(
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground
            )
        ) {
            append("${event.message}")
        }
        append(" [duration: ")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append("${event.duration} s")
        }
        append("]")
    }
    Text(
        text = atext,
        modifier = Modifier
            .padding(start = padding.dp, top = 4.dp)
            .clickable { showEventInfo = !showEventInfo }
    )

    val xpadding = padding + 4
    if (showEventInfo) {
        Text(
            "timestamp : ${event.timestamp}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        Text(
            "datastore : ${event.datastore}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        Text(
            "duration : ${event.duration}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        Text(
            "sessionId : ${event.sessionId}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        Text(
            "transaction-id : ${event.transactionId}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        Text(
            "span-id : ${event.spanId}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        Text(
            "trace-id : ${event.traceId}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        Text(
            "parentSpanId : ${event.parentSpanId}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        /*
        Text(
            "timer : ${event.timer}",
            modifier = Modifier.padding(start = xpadding.dp)
        )
        */
    }
}
