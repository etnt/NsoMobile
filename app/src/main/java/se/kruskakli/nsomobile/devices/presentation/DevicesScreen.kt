package se.kruskakli.nsomobile.devices.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.core.presentation.CenteredProgressIndicator
import se.kruskakli.nsomobile.core.presentation.Divider
import se.kruskakli.nsomobile.core.presentation.Field
import se.kruskakli.nsomobile.core.presentation.InsideCard
import se.kruskakli.nsomobile.core.presentation.OnFailureMessageBox
import se.kruskakli.nsomobile.core.presentation.OutlinedCards
import se.kruskakli.nsomobile.devices.domain.DeviceUi
import se.kruskakli.nsomobile.devices.domain.DevicesIntent
import se.kruskakli.nsomobile.devices.domain.DevicesViewModel



@Composable
fun DevicesScreen(
) {
    val viewModel = koinViewModel<DevicesViewModel>()
    viewModel.handleIntent(DevicesIntent.showDevices)

    DevicesContent(
        viewModel
    )
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DevicesContent(
    viewModel: DevicesViewModel
) {
    val devices by viewModel.nsoDevices.collectAsState()

    devices.DisplayResult(
        onLoading = {
            CenteredProgressIndicator()
        },
        onSuccess = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Divider()
                    val devicesWithVisibility = remember { it.map { it to mutableStateOf(false) } }
                    LazyColumn {
                        devicesWithVisibility.forEach { (device, show) ->
                            stickyHeader {
                                DeviceHeadField(
                                    device = device,
                                    toggleShow = { show.value = !show.value }
                                )
                            }
                            item {
                                if (show.value) {
                                    Device(device)
                                }
                            }
                            item { Divider() }
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
fun Device(
    device: DeviceUi,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            val fields = mutableListOf(
                Field("Last Connected", device.lastConnected),
                Field("Address", device.address),
                Field("Port", device.port),
                Field("Authgroup", device.authgroup),
                Field("Commit Queue Length", device.commitQueue.queueLength),
                Field("Oper State", device.state.operState),
                Field("Admin State", device.state.adminState)
            )
            device.state.transactionMode?.let {
                fields.add(Field("Transaction Mode", it))
            }

            val alarmFields = listOf(
                Field("Indeterminates", device.alarmSummary.indeterminates),
                Field("Criticals", device.alarmSummary.critical),
                Field("Majors", device.alarmSummary.major),
                Field("Minors", device.alarmSummary.minor),
                Field("Warnings", device.alarmSummary.warning)
            )

            OutlinedCards(
                header = "Device Info:",
                fields = fields,
                cards = listOf {
                    InsideCard(
                        header = "Alarm Summary:",
                        fields = alarmFields,
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                    )
                },
                textColor = MaterialTheme.colorScheme.onSurface,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
fun DeviceHeadField(
    device: DeviceUi,
    toggleShow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { toggleShow() })
            .background(MaterialTheme.colorScheme.background)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append("Device: ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(device.name)
            }
            append("  OperState(")
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(device.state.operState)
            }
            append(")")
            append("  Alarms(")
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(device.alarmSummary.sum().toString())
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



