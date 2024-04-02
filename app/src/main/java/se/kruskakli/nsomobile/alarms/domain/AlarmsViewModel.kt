package se.kruskakli.nsomobile.alarms.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.alarms.data.AlarmsRepository
import se.kruskakli.nsomobile.core.domain.DataState
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository


class AlarmsViewModel(
    private val alarmsRepository: AlarmsRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
): ViewModel() {
    private val _nsoAlarms = MutableStateFlow<DataState<List<AlarmUi>>>(DataState.Idle)
    val nsoAlarms = _nsoAlarms.asStateFlow()

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
        DataState.Loading.also { _nsoAlarms.value = it }
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch(Dispatchers.IO) {
                alarmsRepository.getAlarmList(
                    host = systemInfo.ip,
                    port = systemInfo.port,
                    user = systemInfo.user,
                    password = systemInfo.password
                ).onSuccess {
                    val newAlarms = mutableListOf<AlarmUi>()
                    it.nsoAlarmList.alarm.forEach() {
                        val p = it.toAlarmUi()
                        newAlarms.add(p)
                    }
                    DataState.Success(newAlarms).also { newState ->
                        Log.d("AlarmsViewModel", "getAlarmList newState: ${newState.getSuccessData()}")
                        _nsoAlarms.value = newState
                    }
                }.onFailure {
                    DataState.Failure(it).also { newState ->
                        Log.d("AlarmsViewModel", "getAlarmList onFailure: ${newState.getFailureMessage()}")
                        _nsoAlarms.value = newState
                    }
                }
            }
        }
    }

}