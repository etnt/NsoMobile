package se.kruskakli.nsomobile.debug.processes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.Divider
import se.kruskakli.nsomobile.R
import se.kruskakli.nsomobile.core.presentation.CenteredProgressIndicator
import se.kruskakli.nsomobile.core.presentation.Field
import se.kruskakli.nsomobile.core.presentation.InsideCard
import se.kruskakli.nsomobile.core.presentation.OnFailureMessageBox
import se.kruskakli.nsomobile.core.presentation.SortType
import se.kruskakli.nsomobile.debug.processes.domain.NsoProcessesIntent
import se.kruskakli.nsomobile.debug.processes.domain.NsoProcessesViewModel
import se.kruskakli.nsomobile.debug.processes.domain.ProcessUi
import se.kruskakli.nsomobile.devices.domain.DevicesIntent
import se.kruskakli.nsomobile.devices.domain.DevicesViewModel
import se.kruskakli.nsomobile.devices.presentation.DevicesContent


@Composable
fun ProcessesScreen(
) {
    val viewModel = koinViewModel<NsoProcessesViewModel>()
    viewModel.handleIntent(NsoProcessesIntent.ShowProcesses)

    DoProcessScreen(viewModel)
}

@Composable
fun DoProcessScreen(
    viewModel: NsoProcessesViewModel
) {
    val nsoProcesses by viewModel.nsoProcesses.collectAsState()

    nsoProcesses.DisplayResult(
        onLoading = {
            CenteredProgressIndicator()
        },
        onSuccess = { processes ->
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Divider()
                    ProcSortingBar(viewModel)
                    LazyColumn {
                        items(items = processes) {
                            Process(it)
                        }
                    }
                }
            }
        },
        onFailure = {
            OnFailureMessageBox(it)
        }
    )

}

@Composable
fun Process(
    process: ProcessUi
) {
    var show by remember { mutableStateOf(false) }
    val toggleShow = { show = !show }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { toggleShow() })
            .padding(4.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        ProcHeader(process)
        if (show) {
            val fields = listOf(
                Field("Pid", process.pid),
                Field("Name", process.name),
                Field("Reductions", process.reds),
                Field("Memory", process.memory),
                Field("Initial Call", process.icall),
                Field("Current Function", process.ccall),
                Field("Message Queue Len", process.msgs),
                Field("Shared Binaries", process.sharedBinaries ?: "-")
            )
            InsideCard(
                header = "Process Info:",
                fields = fields,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            )
        }
        Divider()
    }
}

@Composable
private fun ProcHeader(
    process: ProcessUi
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (process.name == "") process.pid else process.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(0.dp)
                .weight(2f)
        )
        Text(
            text = process.reds,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(0.dp)
                .weight(1f),
            textAlign = TextAlign.End
        )
        Text(
            text = process.memory,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(0.dp)
                .weight(1f),
            textAlign = TextAlign.End
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcSortingBar(
    viewModel: NsoProcessesViewModel
) {
    var nameSortType by remember { mutableStateOf(SortType.Ascending) }
    var memSortType by remember { mutableStateOf(SortType.Ascending) }
    var redsSortType by remember { mutableStateOf(SortType.Ascending) }

    val arrowUpIcon = ImageVector.vectorResource(id = R.drawable.ic_arrow_drop_up)
    val arrowDownIcon = ImageVector.vectorResource(id = R.drawable.ic_arrow_drop_down)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .clickable {
                    nameSortType =
                        if (nameSortType == SortType.Ascending) SortType.Descending else SortType.Ascending
                    viewModel.handleIntent(
                        NsoProcessesIntent.SortData(
                            "name",
                            nameSortType
                        )
                    )
                }
                .weight(2f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (nameSortType == SortType.Ascending) arrowUpIcon else arrowDownIcon,
                contentDescription = "Sort",
                modifier = Modifier
                    .padding(0.dp),
                tint = Color.Black
            )
            Text(
                text = "Name",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                        .padding(0.dp),
                //.weight(1f),
                //textAlign = TextAlign.Start
            )
        }
        Row(
            modifier = Modifier
                .clickable {
                    redsSortType =
                        if (redsSortType == SortType.Ascending) SortType.Descending else SortType.Ascending
                    viewModel.handleIntent(
                        NsoProcessesIntent.SortData(
                            "reds",
                            redsSortType
                        )
                    )
                },
                //.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (redsSortType == SortType.Ascending) arrowUpIcon else arrowDownIcon,
                contentDescription = "Sort",
                modifier = Modifier
                    .padding(0.dp),
                tint = Color.Black
            )
            Text(
                text = "Reductions",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(0.dp),
                //.weight(1f),
                textAlign = TextAlign.End
            )
        }
        Row(
            modifier = Modifier
                .clickable {
                    memSortType =
                        if (memSortType == SortType.Ascending) SortType.Descending else SortType.Ascending
                    viewModel.handleIntent(
                        NsoProcessesIntent.SortData(
                            "mem",
                            memSortType
                        )
                    )
                }
                .weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (redsSortType == SortType.Ascending) arrowUpIcon else arrowDownIcon,
                contentDescription = "Sort",
                modifier = Modifier
                    .padding(0.dp),
                tint = Color.Black
            )
            Text(
                text = "Memory",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(0.dp),
                //.weight(1f),
                textAlign = TextAlign.End
            )

        }
    }
    Divider()
}