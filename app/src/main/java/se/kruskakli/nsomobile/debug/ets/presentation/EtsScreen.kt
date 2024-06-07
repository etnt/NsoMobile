package se.kruskakli.nsomobile.debug.ets.presentation

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.Divider
import se.kruskakli.nsomobile.core.presentation.CenteredProgressIndicator
import se.kruskakli.nsomobile.core.presentation.Field
import se.kruskakli.nsomobile.core.presentation.InsideCard
import se.kruskakli.nsomobile.core.presentation.OnFailureMessageBox
import se.kruskakli.nsomobile.core.presentation.SortType
import se.kruskakli.nsomobile.debug.ets.domain.NsoEtsIntent
import se.kruskakli.nsomobile.debug.ets.domain.NsoEtsViewModel
import se.kruskakli.nsomobile.debug.ets.domain.EtsUi
import se.kruskakli.nsomobile.main.domain.TabPage


@Composable
fun EtsScreen(
) {
    val viewModel = koinViewModel<NsoEtsViewModel>()
    viewModel.handleIntent(NsoEtsIntent.ShowEts)

    DoEtsScreen(viewModel)
}

@Composable
fun DoEtsScreen(
    viewModel: NsoEtsViewModel
) {
    val nsoEts by viewModel.nsoEts.collectAsState()

    nsoEts.DisplayResult(
        onLoading = {
            CenteredProgressIndicator()
        },
        onSuccess = { ets ->
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Divider()
                    EtsSortingBar(viewModel)
                    LazyColumn {
                        items(items = ets) {
                            Ets(it)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EtsSortingBar(
    viewModel: NsoEtsViewModel
) {
    var nameSortType by remember { mutableStateOf(SortType.Ascending) }
    var memSortType by remember { mutableStateOf(SortType.Ascending) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .padding(0.dp),
            onClick = {
                nameSortType = if (nameSortType == SortType.Ascending) SortType.Descending else SortType.Ascending
                viewModel.handleIntent(NsoEtsIntent.SortData("name", nameSortType))
            }
        ) {
            Icon(
                imageVector = if (nameSortType == SortType.Ascending) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Sort Ascending",
                modifier = Modifier
                    .padding(0.dp),
                tint = Color.Black
            )
        }
        Text(
            text = "ETS Tables",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(0.dp)
        )
        IconButton(
            modifier = Modifier
                .padding(0.dp),
            onClick = {
                memSortType = if (memSortType == SortType.Ascending) SortType.Descending else SortType.Ascending
                viewModel.handleIntent(NsoEtsIntent.SortData("mem", memSortType))
            }
        ) {
            Icon(
                imageVector = if (memSortType == SortType.Ascending) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Sort Descending",
                modifier = Modifier
                    .padding(0.dp),
                tint = Color.Black
            )
        }
    }
}

@Composable
fun Ets(
    ets: EtsUi,
    modifier: Modifier = Modifier
) {
    var show by remember { mutableStateOf(false) }
    val toggleShow = { show = !show }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Divider()
            EtsHeadField(ets, toggleShow)
            if (show) {
                val fields = listOf(
                    Field("Table Name", ets.name),
                    Field("Table Memory", ets.mem + " no.of words"),
                    Field("Table Size", ets.size + " no.of objects"),
                    Field("Table Type", ets.type),
                    Field("Table Owner", ets.owner),
                    Field("Table ID", ets.id)
                )
                InsideCard(
                    header = "ETS tables:",
                    fields = fields,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                )
            }
        }
    }
}


@Composable
fun EtsHeadField(
    ets: EtsUi,
    toggleShow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { toggleShow() })
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ets.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
        )
        Text(
            text = ets.mem,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}


