package se.kruskakli.nsomobile.syscounters.presentation


import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import se.kruskakli.nsomobile.settings.domain.SystemInfo
import se.kruskakli.nsomobile.syscounters.domain.SysCountersUi
import se.kruskakli.nsomobile.syscounters.domain.SysCountersViewModel

@Composable
fun SysCountersScreen(
) {
    val viewModel = koinViewModel<SysCountersViewModel>()
    val systemInfo by viewModel.systemInfo.collectAsState()
    val sysCounters by viewModel.sysCounters.collectAsState()

    viewModel.getSysCounters()

    SysCountersContent(
        systemInfo,
        sysCounters
    )
}

@Composable
fun SysCountersContent(
    systemInfo: SystemInfo?,
    sysCounters: SysCountersUi?
) {
    if (systemInfo != null) {
        Log.d("SysCountersContent", "systemInfo: $systemInfo")
        Log.d("SysCountersContent", "sysCounters: $sysCounters")
        //Text(text = "SysCountersContent: ${systemInfo} -- ${sysCounters}")
    }

    Text(text = "SysCountersContent 2")

}
