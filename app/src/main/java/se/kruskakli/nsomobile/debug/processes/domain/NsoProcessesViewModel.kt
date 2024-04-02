package se.kruskakli.nsomobile.debug.processes.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.core.domain.DataState
import se.kruskakli.nsomobile.core.presentation.SortType
import se.kruskakli.nsomobile.debug.processes.data.NsoProcessRepository
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository

class NsoProcessesViewModel(
    private val nsoProcessRepository: NsoProcessRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
): ViewModel() {

    private val _nsoProcesses = MutableStateFlow<DataState<List<ProcessUi>>>(DataState.Idle)
    val nsoProcesses = _nsoProcesses.asStateFlow()

    init {
        // We only listen for the refresh event that is relevant to us!
        viewModelScope.launch {
            EventChannel.refreshFlow
                .filter { it == TabPage.Processes } // Listen only to relevant screen refreshes
                .collect {
                    refreshContent()
                }
        }
    }

    fun handleIntent(intent: NsoProcessesIntent) {
        when (intent) {
            is NsoProcessesIntent.ShowProcesses -> {
                getNsoProcesses()
            }
            is NsoProcessesIntent.SortData -> {
                val currentProcesses = _nsoProcesses.value
                if (currentProcesses !is DataState.Success) {
                    return
                }
                val sortedProcesses = when (intent.field) {
                    "name" -> currentProcesses.data.sortedBy { it.name }
                    "reds" -> currentProcesses.data.sortedBy { it.reds.toIntOrNull() ?: 0 }
                    "mem" -> currentProcesses.data.sortedBy { it.memory.toIntOrNull() ?: 0 }
                    else -> currentProcesses.data
                }
                if (intent.sortType == SortType.Descending) {
                    _nsoProcesses.value = DataState.Success(sortedProcesses.reversed())
                } else {
                    _nsoProcesses.value = DataState.Success(sortedProcesses)
                }
            }
        }
    }

    private fun refreshContent() {
        getNsoProcesses()
    }

    private fun getNsoProcesses() {
        DataState.Loading.also { _nsoProcesses.value = it }
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch(Dispatchers.IO) {
                nsoProcessRepository.getNsoProcesses(
                    host = systemInfo.ip,
                    port = systemInfo.port,
                    user = systemInfo.user,
                    password = systemInfo.password
                ).onSuccess {
                    val newProcesses = mutableListOf<ProcessUi>()
                    it.nsoAllProcesses.forEach() {
                        val p = it.toProcessUi()
                        newProcesses.add(p)
                    }
                    DataState.Success(newProcesses).also { newState ->
                        Log.d("NsoProcessesViewModel", "getNsoProcesses: ${newState.getSuccessData()}")
                        _nsoProcesses.value = newState
                    }
                }.onFailure {
                    DataState.Failure(it).also { newState ->
                        Log.d("NsoProcessesViewModel", "getNsoProcesses: ${newState.getFailureMessage()}")
                            _nsoProcesses.value = newState
                    }
                }
            }
        }
    }
}
