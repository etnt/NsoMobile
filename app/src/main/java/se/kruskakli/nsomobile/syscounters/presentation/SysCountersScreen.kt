package se.kruskakli.nsomobile.syscounters.presentation


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.core.presentation.Divider
import se.kruskakli.nsomobile.core.presentation.FieldWithHelp
import se.kruskakli.nsomobile.core.presentation.InsideCardWithHelp
import se.kruskakli.nsomobile.core.presentation.OutlinedCards
import se.kruskakli.nsomobile.core.presentation.annotatedText
import se.kruskakli.nsomobile.settings.domain.SystemInfo
import se.kruskakli.nsomobile.syscounters.domain.SysCountersIntent
import se.kruskakli.nsomobile.syscounters.domain.SysCountersUi
import se.kruskakli.nsomobile.syscounters.domain.SysCountersUi.TransactionUi.DatastoreUi.Companion
import se.kruskakli.nsomobile.syscounters.domain.SysCountersViewModel

@Composable
fun SysCountersScreen(
) {
    val viewModel = koinViewModel<SysCountersViewModel>()
    viewModel.handleIntent(SysCountersIntent.ShowSysCounters)

    SysCountersContent(
        viewModel
    )
}

@Composable
fun SysCountersContent(
    viewModel: SysCountersViewModel
) {
    val systemInfo by viewModel.systemInfo.collectAsState()
    val sysCounters by viewModel.sysCounters.collectAsState()

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
                item { Transaction(sysCounters?.transaction) }
                item { ServiceConflicts(sysCounters?.serviceConflicts) }
                item { Cdb(sysCounters?.cdb) }
                item { Device(sysCounters?.device) }
                item { Session(sysCounters?.session) }
            }
        }
    }
}

@Composable
fun ServiceConflicts(
    serviceConflicts: SysCountersUi.ServiceConflictsUi?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val cards: List<@Composable () -> Unit> = serviceConflicts?.serviceType?.map { serviceType ->
            {
                InsideCardWithHelp(
                    header = "Service Type Conflicts:",
                    fields = listOf(
                        serviceType?.name?.let {
                            FieldWithHelp("Name:", it, SysCountersUi.ServiceConflictsUi.ServiceTypeUi.NAME_DESCRIPTION)
                        },
                        serviceType?.conflicts?.let {
                            FieldWithHelp("Conflicts:", it, SysCountersUi.ServiceConflictsUi.ServiceTypeUi.CONFLICTS_DESCRIPTION)
                        }
                    ),
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        } ?: emptyList()
        val annotation = if (serviceConflicts?.serviceType.isNullOrEmpty()) "(no service conflicts to show)" else null
        val annHeader = annotatedText("Service Conflicts: ", annotation)
        OutlinedCards(
            header = "Service Conflicts: ",
            annotatedHeader = annHeader,
            fields = emptyList(),
            cards = cards,
            textColor = MaterialTheme.colorScheme.onSurface,
            color = MaterialTheme.colorScheme.surface,
            show = false
        )
    }
}



@Composable
fun Cdb(
    cdb: SysCountersUi.CdbUi?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val cards: List<@Composable () -> Unit> = listOf(
            {
                InsideCardWithHelp(
                    header = "CDB:",
                    fields = listOf(
                        cdb?.compactions?.let {
                            FieldWithHelp("Compactions:", it, SysCountersUi.CdbUi.COMPACT_DESCRIPTION)
                        },
                        cdb?.compaction?.ACdb?.let {
                            FieldWithHelp("A-CDB Compactions:", it, SysCountersUi.CdbUi.CompactionUi.A_CDB_DESCRIPTION)
                        },
                        cdb?.compaction?.OCdb?.let {
                            FieldWithHelp("O-CDB Compactions:", it, SysCountersUi.CdbUi.CompactionUi.O_CDB_DESCRIPTION)
                        },
                        cdb?.compaction?.SCdb?.let {
                            FieldWithHelp("S-CDB Compactions:", it, SysCountersUi.CdbUi.CompactionUi.S_CDB_DESCRIPTION)
                        },
                        cdb?.bootTime?.let {
                            FieldWithHelp("Boot time:", it, SysCountersUi.CdbUi.BOOT_TIME_DESCRIPTION)
                        },
                        cdb?.phase0Time?.let {
                            FieldWithHelp("Phase 0 time:", it, SysCountersUi.CdbUi.PHASE0_TIME_DESCRIPTION)
                        },
                        cdb?.phase1Time?.let {
                            FieldWithHelp("Phase 1 time:", it, SysCountersUi.CdbUi.PHASE1_TIME_DESCRIPTION)
                        },
                        cdb?.phase2Time?.let {
                            FieldWithHelp("Phase 2 time:", it, SysCountersUi.CdbUi.PHASE2_TIME_DESCRIPTION)
                        }
                    ),
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        )
        OutlinedCards(
            header = "CDB Counters:",
            fields = emptyList(),
            cards = cards,
            textColor = MaterialTheme.colorScheme.onSurface,
            color = MaterialTheme.colorScheme.surface,
            show = false
        )
    }
}



@Composable
fun Device(
    device: SysCountersUi.DeviceUi?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val cards: List<@Composable () -> Unit> = listOf(
            {
                InsideCardWithHelp(
                    header = "Devices:",
                    fields = listOf(
                        device?.connect?.let {
                            FieldWithHelp("Connect:", it, SysCountersUi.DeviceUi.CONNECT_DESCRIPTION)
                        },
                        device?.connectFailed?.let {
                            FieldWithHelp("Connect fail:", it, SysCountersUi.DeviceUi.CONNECT_FAILED_DESCRIPTION)
                        },
                        device?.syncFrom?.let {
                            FieldWithHelp("Sync from:", it, SysCountersUi.DeviceUi.SYNC_FROM_DESCRIPTION)
                        },
                        device?.syncTo?.let {
                            FieldWithHelp("Sync to:", it, SysCountersUi.DeviceUi.SYNC_TO_DESCRIPTION)
                        },
                        device?.outOfSync?.let {
                            FieldWithHelp("Out of sync:", it, SysCountersUi.DeviceUi.OUT_OF_SYNC_DESCRIPTION)
                        }
                    ),
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        )
        OutlinedCards(
            header = "Device Counters:",
            fields = emptyList(),
            cards = cards,
            textColor = MaterialTheme.colorScheme.onSurface,
            color = MaterialTheme.colorScheme.surface,
            show = false
        )
    }
}

@Composable
fun Session(
    session: SysCountersUi.SessionUi?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val cards: List<@Composable () -> Unit> = listOf(
            {
                InsideCardWithHelp(
                    header = "Sessions:",
                    fields = listOf(
                        session?.total?.let {
                            FieldWithHelp("Total:", it, SysCountersUi.SessionUi.TOTAL_DESCRIPTION)
                        },
                        session?.netconfTotal?.let {
                            FieldWithHelp("Netconf total:", it, SysCountersUi.SessionUi.NETCONF_TOTAL_DESCRIPTION)
                        },
                        session?.restconfTotal?.let {
                            FieldWithHelp("Restconf total:", it, SysCountersUi.SessionUi.RESTCONF_TOTAL_DESCRIPTION)
                        },
                        session?.jsonrpcTotal?.let {
                            FieldWithHelp("Jsonrpc total:", it, SysCountersUi.SessionUi.JSONRPC_TOTAL_DESCRIPTION)
                        },
                        session?.snmpTotal?.let {
                            FieldWithHelp("Snmp total:", it, SysCountersUi.SessionUi.SNMP_TOTAL_DESCRIPTION)
                        },
                        session?.cliTotal?.let {
                            FieldWithHelp("Cli total:", it, SysCountersUi.SessionUi.CLI_TOTAL_DESCRIPTION)
                        }
                    ),
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        )
        OutlinedCards(
            header = "Session Counters:",
            fields = emptyList(),
            cards = cards,
            textColor = MaterialTheme.colorScheme.onSurface,
            color = MaterialTheme.colorScheme.surface,
            show = false
        )
    }
}

@Composable
fun Transaction(
    transaction: SysCountersUi.TransactionUi?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val ds: List<@Composable () -> Unit> = transaction?.datastore?.map { datastore ->
            {
                InsideCardWithHelp(
                    header = datastore!!.name,
                    fields = listOf(
                        datastore.commit?.let {
                            FieldWithHelp("Commit:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.COMMIT_DESCRIPTION)
                        },
                        datastore.totalTime?.let {
                            FieldWithHelp("Total time:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.TOTAL_TIME_DESCRIPTION)
                        },
                        datastore.serviceTime?.let {
                            FieldWithHelp("Service time:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.SERVICE_TIME_DESCRIPTION)
                        },
                        datastore.validationTime?.let {
                            FieldWithHelp("Validation time:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.VALIDATION_TIME_DESCRIPTION)
                        },
                        datastore.globalLockTime?.let {
                            FieldWithHelp("Global lock time:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.GLOBAL_LOCK_TIME_DESCRIPTION)
                        },
                        datastore.globalLockWaitTime?.let {
                            FieldWithHelp("Global lock wait time:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.GLOBAL_LOCK_WAIT_TIME_DESCRIPTION)
                        },
                        datastore.aborts?.let {
                            FieldWithHelp("Aborts:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.ABORTS_DESCRIPTION)
                        },
                        datastore.conflicts?.let {
                            FieldWithHelp("Conflicts:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.CONFLICTS_DESCRIPTION)
                        },
                        datastore.retries?.let {
                            FieldWithHelp("Retries:", "${it}", SysCountersUi.TransactionUi.DatastoreUi.RETRIES_DESCRIPTION)
                        },
                    ),
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        } ?: emptyList()
        OutlinedCards(
            header = "Transactional Datastores:",
            fields = emptyList(),
            cards = ds,
            textColor = MaterialTheme.colorScheme.onSurface,
            color = MaterialTheme.colorScheme.surface,
            show = false
        )
    }
}

