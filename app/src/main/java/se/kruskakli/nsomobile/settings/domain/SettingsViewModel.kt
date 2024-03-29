package se.kruskakli.nsomobile.settings.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.core.presentation.isIPv4Address
import se.kruskakli.nsomobile.core.presentation.isNumber
import se.kruskakli.nsomobile.settings.data.SettingsData
import se.kruskakli.nsomobile.settings.data.SettingsRepository
import se.kruskakli.nsomobile.settings.presentation.SettingsDataUI

class SettingsViewModel(
    private val repository: SettingsRepository,
    private val systemInfoRepository: SystemInfoRepository
) : ViewModel() {

    // Create an observable state flow for the settings.
    private val _settings = MutableStateFlow<List<SettingsDataUI>>(emptyList())
    val settings: StateFlow<List<SettingsDataUI>> = _settings.asStateFlow()

    private val _sysNames = MutableStateFlow<List<String>>(emptyList())
    val sysNames: StateFlow<List<String>> = _sysNames.asStateFlow()

    // Create an observable state flow for a new Settings item.
    private val _newState = MutableStateFlow<SettingsState>(SettingsState())
    val newState: StateFlow<SettingsState> = _newState.asStateFlow()

    // Create an observable state flow for the currently active system.
    private val _currentSystem = MutableStateFlow<SystemInfo?>(systemInfoRepository.getSystemInfo())
    val currentSystem: StateFlow<SystemInfo?> = _currentSystem.asStateFlow()

    init {
        loadSettings()
        viewModelScope.launch {
            _settings.map { settingsList ->
                settingsList.map { it.name }
            }.collect { names ->
                _sysNames.value = names
            }
        }
    }

    private fun setCurrentSystem(systemName: String) {
        val systemInfo = _settings.value.firstOrNull { it.name == systemName }?.toSystemInfo()
        if (systemInfo != null) {
            systemInfoRepository.setSystemInfo(systemInfo)
            _currentSystem.value = systemInfo
        }
    }

    // Load the settings from the repository (encrypted shared preferences)
    private fun loadSettings() {
        viewModelScope.launch {
            val sdata = repository.getSettings()
            // Convert the data to UI data
            _settings.value = sdata.map {
                SettingsDataUI(it)
            }
            Log.d("SettingsViewModel", "loadSettings: ${_settings.value} , ${_settings.value.size} ")
            if (_settings.value.isNotEmpty()) {
                if (_currentSystem.value == null) {
                    // If no current system is set, set the first system in the list as the current system
                    systemInfoRepository.setSystemInfo(_settings.value[0].toSystemInfo())
                    _currentSystem.value = _settings.value[0].toSystemInfo()
                }
            }
        }
    }

    fun handleIntent(intent: SettingsIntent) {
        when (intent) {

            is SettingsIntent.SetCurrentSystemInfo -> {
                setCurrentSystem(intent.systemName)
            }

            is SettingsIntent.RemoveSetting -> {
                viewModelScope.launch {
                    val settingsDataUIList = _settings.value
                    val settingsDataList =  settingsDataUIList.map { it.toSettingsData() }
                    val data = settingsDataList.filter { it.name != intent.name }
                    repository.saveSettings(data)
                    loadSettings()
                }
            }

            is SettingsIntent.SaveSettings -> {
                val state = _newState.value
                if (state.nameError == null && state.ipError == null && state.portError == null && state.userError == null && state.passwdError == null) {
                    // Prepare the data to be saved
                    val settingsDataUIList = _settings.value
                    val settingsDataList =  settingsDataUIList.map { it.toSettingsData() }

                    // Save the data
                    viewModelScope.launch {
                        val data = SettingsData(
                            name = state.name,
                            ip = state.ip,
                            port = state.port,
                            user = state.user,
                            passwd = state.passwd
                        )
                        // Add the new data to the list of settings data
                        repository.saveSettings( settingsDataList + data)
                        // Reload the settings
                        loadSettings()
                    }

                    // Reset the SettingsState
                    _newState.value = SettingsState()
                }
            }

            is SettingsIntent.SetFieldValue -> {
                // Validate the field and update the state.
                // The idea is that no validation should be done in the UI.
                // Instead the UI should just send the intent to the ViewModel
                // and all logic should be handled here.
                // So for each field that changes, we do the validation here and
                // possibly generate an error message that is sent back to the UI.
                when (intent.field) {
                    "name" -> {
                        if (intent.value.isEmpty()) {
                            _newState.value = _newState.value.copy(name = intent.value, nameError = "Name cannot be empty")
                        } else {
                            _newState.value = _newState.value.copy(name = intent.value, nameError = null)
                        }
                    }
                    "ip" -> {
                        if (isIPv4Address(intent.value)) {
                            _newState.value = _newState.value.copy(ip = intent.value, ipError = null)
                        } else {
                            _newState.value = _newState.value.copy(ip = intent.value, ipError = "Not a valid IPv4 address")
                        }
                    }
                    "port" -> {
                        if (isNumber(intent.value)) {
                            _newState.value = _newState.value.copy(port = intent.value, portError = null)
                        } else {
                            _newState.value = _newState.value.copy(port = intent.value, portError = "Port must be a number")
                        }
                    }
                    "user" -> {
                        if (intent.value.isEmpty()) {
                            _newState.value = _newState.value.copy(user = intent.value, userError = "User cannot be empty")
                        } else {
                            _newState.value = _newState.value.copy(user = intent.value, userError = null)
                        }
                    }
                    "passwd" -> {
                        if (intent.value.isEmpty()) {
                            _newState.value = _newState.value.copy(passwd = intent.value, passwdError = "Password cannot be empty")
                        } else {
                            _newState.value = _newState.value.copy(passwd = intent.value, passwdError = null)
                        }
                    }
                }
            }
        }
    }
}

