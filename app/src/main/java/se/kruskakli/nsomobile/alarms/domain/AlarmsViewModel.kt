package se.kruskakli.nsomobile.alarms.domain

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nso.data.alarms.toAlarmUi
import se.kruskakli.nsomobile.alarms.data.AlarmsRepository
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository
import se.kruskakli.nsomobile.syscounters.data.toUiModel
import se.kruskakli.nsomobile.syscounters.domain.SysCountersIntent


class AlarmsViewModel(
    private val alarmsRepository: AlarmsRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
) {
    private val _nsoAlarms = MutableStateFlow(listOf<AlarmUi>())
    val nsoAlarms: StateFlow<List<AlarmUi>> = _nsoAlarms.asStateFlow()

    init {
        // We only listen for the refresh event that is relevant to us!
        viewModelScope.launch {
            EventChannel.refreshFlow
                .filter { it == TabPage.Alarms } // Listen only to relevant screen refreshes
                .collect {
                    refreshContent()
                }
        }
    }

    fun handleIntent(intent: AlarmsIntent) {
        when (intent) {
            is AlarmsIntent.ShowAlarms -> {
                getAlarmList()
            }
        }
    }

    private fun refreshContent() {
        //Log.d("SysCountersViewModel", "refreshContent")
        getAlarmList()
    }

    private fun getAlarmList() {
        val systemInfo = systemInfoRepository.getSystemInfo()
        Log.d("AlarmsViewModel", "getAlarmList systemInfo: $systemInfo")
        if (systemInfo != null) {
            Log.d("AlarmsViewModel", "getAlarmList systemInfo: ${systemInfo}")
            //_loading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                alarmsRepository.getAlarmList(
                    host = systemInfo.ip,
                    port = systemInfo.port,
                    user = systemInfo.user,
                    password = systemInfo.password
                ).onSuccess {
                    Log.d("AlarmsViewModel", "getAlarmList onSuccess: $it")
                    val newAlarms = mutableListOf<AlarmUi>()
                    it.nsoAlarmList.alarm.forEach() {
                        //Log.d("MainViewModel", "getNsoDevices BODY: ${it}")
                        val p = it.toAlarmUi()
                        newAlarms.add(p)
                    }
                    _nsoAlarms.value = newAlarms
                }.onFailure {
                    Log.d("AlarmsViewModel", "getAlarmList onFailure: $it")
                    _nsoAlarms.value = null
                }
            }
        }
        Log.d("SysCountersViewModel", "getSysCounters end")
    }

}