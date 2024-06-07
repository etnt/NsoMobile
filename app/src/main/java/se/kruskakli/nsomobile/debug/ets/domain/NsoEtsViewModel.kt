package se.kruskakli.nsomobile.debug.ets.domain

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
import se.kruskakli.nsomobile.debug.ets.data.NsoEtsRepository
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository

class NsoEtsViewModel(
    private val nsoEtsRepository: NsoEtsRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
): ViewModel() {

    private val _nsoEts = MutableStateFlow<DataState<List<EtsUi>>>(DataState.Idle)
    val nsoEts = _nsoEts.asStateFlow()


    init {
        // We only listen for the refresh event that is relevant to us!
        viewModelScope.launch {
            EventChannel.refreshFlow
                .filter { it == TabPage.EtsTables } // Listen only to relevant screen refreshes
                .collect {
                    refreshContent()
                }
        }
    }

    fun handleIntent(intent: NsoEtsIntent) {
        when (intent) {
            is NsoEtsIntent.ShowEts -> {
                getNsoEts()
            }
            is NsoEtsIntent.SortData -> {
                val currentEts = _nsoEts.value
                if (currentEts !is DataState.Success) {
                    return
                }
                Log.d("NsoEtsViewModel", "SortData: field=${intent.field} ${intent.sortType}")
                val sortedEts = when (intent.field) {
                    "name" -> currentEts.data.sortedBy { it.name }
                    "mem" -> currentEts.data.sortedBy { it.mem.toIntOrNull() ?: 0 }
                    else -> currentEts.data
                }
                if (intent.sortType == SortType.Descending) {
                    _nsoEts.value = DataState.Success(sortedEts.reversed())
                } else {
                    _nsoEts.value = DataState.Success(sortedEts)
                }
            }
        }
    }

    private fun refreshContent() {
        getNsoEts()
    }

    private fun getNsoEts() {
        DataState.Loading.also { _nsoEts.value = it }
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch(Dispatchers.IO) {
                nsoEtsRepository.getNsoEts(
                    host = systemInfo.ip,
                    port = systemInfo.port,
                    user = systemInfo.user,
                    password = systemInfo.password
                ).onSuccess {
                    val newEts = mutableListOf<EtsUi>()
                    it.nsoEtsTables.all.forEach() {
                        val p = it.toEtsUi()
                        newEts.add(p)
                    }
                    DataState.Success(newEts).also { newState ->
                        Log.d("NsoEtsViewModel", "getNsoEts: ${newState.getSuccessData()}")
                        _nsoEts.value = newState
                    }
                }.onFailure {
                    DataState.Failure(it).also { newState ->
                        Log.d("NsoEtsViewModel", "getNsoEts: ${newState.getFailureMessage()}")
                        _nsoEts.value = newState
                    }
                }
            }
        }
    }
}