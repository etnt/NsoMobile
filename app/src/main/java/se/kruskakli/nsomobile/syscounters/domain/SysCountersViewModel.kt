package se.kruskakli.nsomobile.syscounters.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepository

class SysCountersViewModel(
    private val sysCountersRepository: SysCountersRepository
) : ViewModel() {

    private val _sysCounters = MutableStateFlow<SysCountersUi?>(null)
    val sysCounters: StateFlow<SysCountersUi?> = _sysCounters.asStateFlow()

    fun resetNsoSysCounters() {
        _sysCounters.value = null
    }

    fun getSysCounters() {
        viewModelScope.launch(Dispatchers.IO) {
            //_loading.value = true
            sysCountersRepository.getSysCounters(
                host = _host.value,                    // FIXME: provide current active settings from settingsViewModel!?
                port = _port.value,
                user = _user.value,
                password = _passwd.value,
                path = "restconf/data/tailf-ncs:metric/sysadmin/counter",
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