package se.kruskakli.nsomobile.devices.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.core.domain.DataState
import se.kruskakli.nsomobile.devices.data.NsoDevicesRepository
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.nsopackage.domain.PackageIntent
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository

class DevicesViewModel(
    private val devicesRepository: NsoDevicesRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
): ViewModel() {

    private val _nsoDevices = MutableStateFlow<DataState<List<DeviceUi>>>(DataState.Idle)
    val nsoDevices = _nsoDevices.asStateFlow()

    init {
        // We only listen for the refresh event that is relevant to us!
        viewModelScope.launch {
            EventChannel.refreshFlow
                .filter { it == TabPage.Devices } // Listen only to relevant screen refreshes
                .collect {
                    refreshContent()
                }
        }
    }

    fun handleIntent(intent: DevicesIntent) {
        when (intent) {
            is DevicesIntent.showDevices -> {
                getNsoDevices()
            }
        }
    }

    private fun refreshContent() {
        getNsoDevices()
    }

    private fun getNsoDevices() {
        DataState.Loading.also { _nsoDevices.value = it }
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch {
                val result = devicesRepository.getNsoDevices(
                    systemInfo.ip,
                    systemInfo.port,
                    systemInfo.user,
                    systemInfo.password
                ).onSuccess {
                    val newDevices = mutableListOf<DeviceUi>()
                    it.nsoDevices.devices.forEach() {
                        val p = it.toDeviceUi()
                        newDevices.add(p)
                    }
                    DataState.Success(newDevices).also { newState ->
                        Log.d("PackageViewModel", "getNsoDevices onSuccess: ${newState.getSuccesData()}")
                        _nsoDevices.value = newState
                    }
                }.onFailure {
                    DataState.Failure(it).also { newState ->
                        Log.d("DevicesViewModel", "getNsoDevices onFailure: ${newState.getFailureMessage()}")
                        _nsoDevices.value = newState
                    }
                }
            }
        }
    }

}