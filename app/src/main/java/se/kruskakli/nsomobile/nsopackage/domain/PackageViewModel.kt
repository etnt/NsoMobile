package se.kruskakli.nsomobile.nsopackage.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.alarms.domain.AlarmUi
import se.kruskakli.nsomobile.alarms.domain.AlarmsIntent
import se.kruskakli.nsomobile.core.data.ApiOperation
import se.kruskakli.nsomobile.core.domain.DataState
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.nsopackage.data.NsoPackageRepository
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository

class PackageViewModel(
    private val packageRepository: NsoPackageRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
): ViewModel() {

    private val _nsoPackages = MutableStateFlow<DataState<List<PackageUi>>>(DataState.Idle)
    val nsoPackages = _nsoPackages.asStateFlow()

    // To make it possible to enable/disable the debug menus
    private val _nsoDbgEnabled = MutableStateFlow(false)
    val nsoDbgEnabled: StateFlow<Boolean> = _nsoDbgEnabled.asStateFlow()

    init {
        // We only listen for the refresh event that is relevant to us!
        viewModelScope.launch {
            EventChannel.refreshFlow
                .filter { it == TabPage.Packages } // Listen only to relevant screen refreshes
                .collect {
                    refreshContent()
                }
        }
    }

    fun handleIntent(intent: PackageIntent) {
        when (intent) {
            is PackageIntent.showPackages -> {
                getNsoPackages()
            }
        }
    }

    private fun refreshContent() {
        Log.d("PackageViewModel", "refreshContent")
        getNsoPackages()
    }

    private fun getNsoPackages() {
        DataState.Loading.also { _nsoPackages.value = it }
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch {
                val result = packageRepository.getNsoPackages(
                    systemInfo.ip,
                    systemInfo.port,
                    systemInfo.user,
                    systemInfo.password
                ).onSuccess {
                    val newPackages = mutableListOf<PackageUi>()
                    it.tailfNcsPackages.nsoPackages.forEach() {
                        val p = it.toPackageUi()
                        newPackages.add(p)
                    }
                    DataState.Success(newPackages).also { newState ->
                        Log.d("PackageViewModel", "getNsoPackages onSuccess: ${newState.getSuccesData()}")
                        _nsoPackages.value = newState
                    }
                    // Check if the nso_dbg package is installed
                    newPackages.forEach { packageUi ->
                        if (packageUi.name == "nso_dbg") {
                            _nsoDbgEnabled.value = true
                            return@forEach
                        }
                    }
                }.onFailure {
                    DataState.Failure(it).also { newState ->
                        Log.d("PackageViewModel", "getNsoPackages onFailure: ${newState.getFailureMessage()}")
                        _nsoPackages.value = newState
                    }
                    _nsoDbgEnabled.value = false
                }
            }
        }
    }
}