package se.kruskakli.nsomobile.debug.inet.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.core.domain.DataState
import se.kruskakli.nsomobile.debug.inet.data.NsoInetRepository
import se.kruskakli.nsomobile.debug.processes.domain.NsoProcessesIntent
import se.kruskakli.nsomobile.debug.processes.domain.ProcessUi
import se.kruskakli.nsomobile.debug.processes.domain.toProcessUi
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository

class NsoInetViewModel(
    private val nsoInetRepository: NsoInetRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
): ViewModel() {

    private val _nsoInet = MutableStateFlow<DataState<List<InetUi>>>(DataState.Idle)
    val nsoInet = _nsoInet.asStateFlow()


    fun handleIntent(intent: NsoInetIntent) {
        when (intent) {
            is NsoInetIntent.ShowInet -> {
                getNsoInet()
            }
        }
    }

    private fun getNsoInet() {
        DataState.Loading.also { _nsoInet.value = it }
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch(Dispatchers.IO) {
                nsoInetRepository.getNsoInet(
                    host = systemInfo.ip,
                    port = systemInfo.port,
                    user = systemInfo.user,
                    password = systemInfo.password
                ).onSuccess {
                    val newInet = mutableListOf<InetUi>()
                    it.all.forEach() {
                        val p = it.toInetUi()
                        newInet.add(p)
                    }
                    DataState.Success(newInet).also { newState ->
                        Log.d("NsoInetViewModel", "getNsoInet: ${newState.getSuccessData()}")
                        _nsoInet.value = newState
                    }
                }.onFailure {
                    DataState.Failure(it).also { newState ->
                        Log.d("NsoInetViewModel", "getNsoInet: ${newState.getFailureMessage()}")
                        _nsoInet.value = newState
                    }
                }
            }
        }
    }
}