package se.kruskakli.nsomobile.syscounters.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepository

class SysCountersViewModel(
    private val sysCountersRepository: SysCountersRepository,
    private val systemInfoRepository: SystemInfoRepository
) : ViewModel() {

    private val _sysCounters = MutableStateFlow<SysCountersUi?>(null)
    val sysCounters: StateFlow<SysCountersUi?> = _sysCounters.asStateFlow()

    fun resetNsoSysCounters() {
        _sysCounters.value = null
    }

    fun getSysCounters() {
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch(Dispatchers.IO) {            //_loading.value = true
                sysCountersRepository.getSysCounters(
                    host = systemInfo.ip,
                    port = systemInfo.port,
                    user = systemInfo.user,
                    password = systemInfo.password,
                    onSuccess = { response ->
                        //Log.d("MainViewModel", "getSysCounters BODY: ${response}")
                        _sysCounters.value = response.body<SysCountersUi>()
                        Log.d("SysCountersViewModel", "getSysCounters UI: ${_sysCounters.value}")
                        //_loading.value = false
                    },
                    onError = {
                        //_loading.value = false
                    }
                )
            }
        }
    }
}