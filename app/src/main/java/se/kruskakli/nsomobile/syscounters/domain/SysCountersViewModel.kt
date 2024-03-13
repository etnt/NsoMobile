package se.kruskakli.nsomobile.syscounters.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.settings.domain.SettingsIntent
import se.kruskakli.nsomobile.settings.domain.SystemInfo
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepository
import se.kruskakli.nsomobile.syscounters.data.toUiModel

class SysCountersViewModel(
    private val sysCountersRepository: SysCountersRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
) : ViewModel() {

    private val _systemInfo = MutableStateFlow(systemInfoRepository.getSystemInfo())
    val systemInfo: StateFlow<SystemInfo?> = _systemInfo.asStateFlow()

    private val _sysCounters = MutableStateFlow<SysCountersUi?>(null)
    val sysCounters: StateFlow<SysCountersUi?> = _sysCounters.asStateFlow()


    init {
        // We only listen for the refresh event that is relevant to us!
        viewModelScope.launch {
            EventChannel.refreshFlow
                .filter { it == TabPage.SysCounters } // Listen only to relevant screen refreshes
                .collect {
                    refreshContent()
                }
        }
    }

    private fun refreshContent() {
        //Log.d("SysCountersViewModel", "refreshContent")
        getSysCounters()
    }


    fun handleIntent(intent: SysCountersIntent) {
        when (intent) {
            is SysCountersIntent.ShowSysCounters -> {
                getSysCounters()
            }
        }
    }

    fun resetNsoSysCounters() {
        _sysCounters.value = null
    }

    private fun getSysCounters() {
        Log.d("SysCountersViewModel", "getSysCounters")
        val systemInfo = systemInfoRepository.getSystemInfo()
        Log.d("SysCountersViewModel", "getSysCounters systemInfo: $systemInfo")
        if (systemInfo != null) {
            Log.d("SysCountersViewModel", "getSysCounters systemInfo: ${systemInfo}")
            //_loading.value = true
            viewModelScope.launch(Dispatchers.IO) {
                sysCountersRepository.getSysCounters(
                    host = systemInfo.ip,
                    port = systemInfo.port,
                    user = systemInfo.user,
                    password = systemInfo.password
                ).onSuccess {
                    Log.d("SysCountersViewModel", "getSysCounters onSuccess: $it")
                    val sysCountersUi = it.sysCounters?.toUiModel()
                    _sysCounters.value = sysCountersUi
                }.onFailure {
                    Log.d("SysCountersViewModel", "getSysCounters onFailure: $it")
                    _sysCounters.value = null
                }
            }
        }
        Log.d("SysCountersViewModel", "getSysCounters end")
    }
}