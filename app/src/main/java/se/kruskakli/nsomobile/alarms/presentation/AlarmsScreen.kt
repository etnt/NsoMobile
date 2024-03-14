package se.kruskakli.nsomobile.alarms.presentation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import se.kruskakli.nso.domain.AlarmUi
import androidx.compose.ui.text.font.FontStyle


@Composable
fun AlarmsScreen(
    nsoAlarms: List<AlarmUi>,
    modifier: Modifier = Modifier
) {
    Alarms(nsoAlarms)
}

@Composable
fun Alarms(
    alarms: List<AlarmUi>,
    modifier: Modifier = Modifier
) {
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
            LazyColumn {
                items(items = alarms) {
                    Alarm(it)
                }
            }
        }
    }
}

@Composable
fun Alarm(
    alarm: AlarmUi,
) {
    var show by remember { mutableStateOf(false) }
    val toggleShow = { show = !show }

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
                AlarmsHeadField(alarm, toggleShow)
                if (show) {
                    val fields = listOf(
                        Field("Last Alarm Text", alarm.lastAlarmText),
                        Field("Is Cleared", alarm.isCleared),
                        Field("Last Perceived Severity", alarm.lastPerceivedSeverity),
                        Field("Last Status Change", alarm.lastStatusChange),
                        Field("Managed Object", alarm.managedObject),
                        Field("Specific Problem", alarm.specificProblem),
                        Field("Type", alarm.type)
                    )
                    
                    val statusChanges: List<@Composable () -> Unit> = alarm.statusChange.map() {
                        {
                            InsideCard(
                                header = "Status Change:",
                                fields = listOf(
                                    Field("Alarm Text", it.alarmText),
                                    Field("Event Time", it.eventTime),
                                    Field("Perceived Severity", it.perceivedSeverity),
                                    Field("Received Time", it.receivedTime)
                                ),
                                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                color = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                    OutlinedCards(
                        header = "Device Info:",
                        fields = fields,
                        cards = statusChanges,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        color = MaterialTheme.colorScheme.surface
                    )
                }
            }
    }
}


@Composable
fun AlarmsHeadField(
    alarm: AlarmUi,
    toggleShow: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { toggleShow() })
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
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
                append(alarm.device)
            }
            append("  Severity(")
            withStyle(
                style = SpanStyle(
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(alarm.lastPerceivedSeverity)
            }
            append(")")
            append("  Cleared(")
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                append(alarm.isCleared)
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onBackground
                )
            ) {
                append(")  - ")
            }
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Italic
                )
            ) {
                append(alarm.lastAlarmText)
            }
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

