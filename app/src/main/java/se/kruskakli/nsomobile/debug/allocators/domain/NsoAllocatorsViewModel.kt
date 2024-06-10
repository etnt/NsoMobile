package se.kruskakli.nsomobile.debug.allocators.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.core.domain.DataState
import se.kruskakli.nsomobile.debug.allocators.data.NsoAllocatorsRepository
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository


class NsoAllocatorsViewModel(
    private val nsoAllocatorsRepository: NsoAllocatorsRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
): ViewModel() {

    private val _nsoAllocators = MutableStateFlow<DataState<AllocatorUi>>(DataState.Idle)
    val nsoAllocators = _nsoAllocators.asStateFlow()


    init {
        // We only listen for the refresh event that is relevant to us!
        viewModelScope.launch {
            EventChannel.refreshFlow
                .filter { it == TabPage.Allocators } // Listen only to relevant screen refreshes
                .collect {
                    refreshContent()
                }
        }
    }

    fun handleIntent(intent: NsoAllocatorsIntent) {
        when (intent) {
            is NsoAllocatorsIntent.ShowAllocators -> {
                getNsoAllocators()
            }
        }
    }

    private fun refreshContent() {
        getNsoAllocators()
    }

    private fun getNsoAllocators() {
        DataState.Loading.also { _nsoAllocators.value = it }
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch(Dispatchers.IO) {
                nsoAllocatorsRepository.getNsoAllocators(
                    host = systemInfo.ip,
                    port = systemInfo.port,
                    user = systemInfo.user,
                    password = systemInfo.password
                ).onSuccess {
                    Log.d("NsoAllocatorsViewModel", "getNsoAllocators: ${it.nsoAllocators.toAllocatorUi()}")
                    DataState.Success(it.nsoAllocators.toAllocatorUi()).also { _nsoAllocators.value = it }
                }.onFailure {
                    DataState.Failure(it).also { newState ->
                        Log.d("NsoAllocatorsViewModel", "getNsoAllocators: ${newState.getFailureMessage()}")
                        _nsoAllocators.value = newState
                    }
                }
            }
        }
    }
}