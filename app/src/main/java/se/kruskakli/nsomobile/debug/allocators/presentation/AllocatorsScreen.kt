package se.kruskakli.nsomobile.debug.allocators.presentation

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.Divider
import se.kruskakli.nsomobile.core.presentation.CenteredProgressIndicator
import se.kruskakli.nsomobile.core.presentation.Field
import se.kruskakli.nsomobile.core.presentation.InsideCard
import se.kruskakli.nsomobile.core.presentation.OnFailureMessageBox
import se.kruskakli.nsomobile.core.presentation.OutlinedCards
import se.kruskakli.nsomobile.debug.allocators.domain.AllocatorUi
import se.kruskakli.nsomobile.debug.allocators.domain.NsoAllocatorsIntent
import se.kruskakli.nsomobile.debug.allocators.domain.NsoAllocatorsViewModel
import kotlin.math.round


@Composable
fun AllocatorsScreen(
) {
    val viewModel = koinViewModel<NsoAllocatorsViewModel>()
    viewModel.handleIntent(NsoAllocatorsIntent.ShowAllocators)

    DoAllocatorsScreen(viewModel)
}

@Composable
fun DoAllocatorsScreen(
    viewModel: NsoAllocatorsViewModel
) {
    val nsoAllocators by viewModel.nsoAllocators.collectAsState()

    nsoAllocators.DisplayResult(
        onLoading = {
            CenteredProgressIndicator()
        },
        onSuccess = { nsoAllocators ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Divider()
                    val groupedAllocators: Map<String, List<AllocatorUi.NsoAllocator>> = nsoAllocators?.allocators?.groupBy { it.name } ?: emptyMap()
                    val allocatorCards: List<@Composable () -> Unit> = groupedAllocators.map { (name, nsoAllocator) ->
                        {
                            Allocators(name, nsoAllocator)
                        }
                    }
                    LazyColumn {
                        items(allocatorCards) { AllocatorCard ->
                            AllocatorCard()
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
fun Allocators(
    name: String,
    nsoAllocators: List<AllocatorUi.NsoAllocator>,
    modifier: Modifier = Modifier,
) {
    var show by remember { mutableStateOf(false) }
    val toggleShow = { show = !show }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // The multiplication by 10 and the division by 10 are used to shift the decimal point to the right before rounding and then shift it back to the left after rounding. This results in rounding to one decimal place.
            val averageUtilization = (round(nsoAllocators.map { it.utilization.toDouble() }.average() * 10) / 10)
            AllocatorHeader(name, nsoAllocators.size, averageUtilization, toggleShow)
            if (show) {
                val allocatorCards: List<@Composable () -> Unit> = nsoAllocators.map() { allocator ->
                    {
                        InsideCard(
                            header = "${name}(${allocator.instance})",
                            fields = listOf(
                                Field("utilization", allocator.utilization + "%" ),
                                Field("blocks-size", allocator.blocksSize),
                                Field("carriers-size", allocator.carriersSize),
                                Field("mbcs", allocator.mbcs.utilization),
                                Field("sbcs", allocator.sbcs.utilization)
                            ),
                            textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp)
                        )
                    }
                }
                OutlinedCards(
                    header = "${name} instances(${nsoAllocators.size}):",
                    fields = emptyList(),
                    cards = allocatorCards,
                    textColor = MaterialTheme.colorScheme.onSurface,
                    color = MaterialTheme.colorScheme.surface
                )
            }
            Divider()
        }
    }
}

@Composable
fun AllocatorHeader(
    name: String,
    noOfInstances: Int,
    avgUtilization: Double,
    toggleShow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = toggleShow)
            .padding(6.dp)
    ) {
        val text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append("ERTS Allocator: ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(name)
            }
            append("  instances(")
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(noOfInstances.toString())
            }
            append(") avgUtil(")
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append("${avgUtilization}%")
            }
            append(")")
        }

        Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp)
        )
    }
}

