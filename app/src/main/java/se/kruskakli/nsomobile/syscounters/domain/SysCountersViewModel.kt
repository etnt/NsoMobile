package se.kruskakli.nsomobile.syscounters.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.settings.domain.SystemInfo
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepository
import se.kruskakli.nsomobile.syscounters.data.toUiModel

class SysCountersViewModel(
    private val sysCountersRepository: SysCountersRepository,
    private val systemInfoRepository: SystemInfoRepository
) : ViewModel() {

    private val _systemInfo = MutableStateFlow(systemInfoRepository.getSystemInfo())
    val systemInfo: StateFlow<SystemInfo?> = _systemInfo.asStateFlow()

    private val _sysCounters = MutableStateFlow<SysCountersUi?>(null)
    val sysCounters: StateFlow<SysCountersUi?> = _sysCounters.asStateFlow()

    fun resetNsoSysCounters() {
        _sysCounters.value = null
    }

    fun getSysCounters() {
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
                    val sysCountersUi = it.sysCounters?.toUiModel()
                    _sysCounters.value = sysCountersUi
                }.onFailure {
                    _sysCounters.value = null
                }
            }
        }
        Log.d("SysCountersViewModel", "getSysCounters end")
    }
}