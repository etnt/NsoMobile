package se.kruskakli.nsomobile.settings.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.core.presentation.isIPv4Address
import se.kruskakli.nsomobile.settings.data.SettingsData
import se.kruskakli.nsomobile.settings.data.SettingsRepository
import se.kruskakli.nsomobile.settings.presentation.SettingsDataUI

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _settings = MutableStateFlow<List<SettingsDataUI>>(emptyList())
    val settings: StateFlow<List<SettingsDataUI>> = _settings.asStateFlow()

    private val _newState = MutableStateFlow<SettingsState>(SettingsState())
    val newState: StateFlow<SettingsState> = _newState.asStateFlow()

    init {
        loadSettings()
    }

    fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.SetFieldValue -> {
                when (intent.field) {
                    "name" -> {
                        if (intent.value.isEmpty()) {
                            _newState.value = _newState.value.copy(name = intent.value, nameError = "Name cannot be empty")
                        } else {
                            _newState.value = _newState.value.copy(name = intent.value, nameError = null)
                        }
                        _newState.value = _newState.value.copy()
                    }
                    "ip" -> {
                        if (isIPv4Address(intent.value)) {
                            _newState.value = _newState.value.copy(ip = intent.value, ipError = null)
                        } else {
                            _newState.value = _newState.value.copy(ip = intent.value, ipError = "Not a valid IPv4 address")
                        }
                    }
                    "port" -> {
                        if (intent.value.isEmpty()) {
                            _newState.value = _newState.value.copy(port = intent.value, portError = "Port cannot be empty")
                        } else {
                            _newState.value = _newState.value.copy(port = intent.value, portError = null)
                        }
                        _newState.value = _newState.value.copy()
                    }
                    "user" -> {
                        if (intent.value.isEmpty()) {
                            _newState.value = _newState.value.copy(user = intent.value, userError = "User cannot be empty")
                        } else {
                            _newState.value = _newState.value.copy(user = intent.value, userError = null)
                        }
                        _newState.value = _newState.value.copy()
                    }
                    "passwd" -> {
                        if (intent.value.isEmpty()) {
                            _newState.value = _newState.value.copy(passwd = intent.value, passwdError = "Password cannot be empty")
                        } else {
                            _newState.value = _newState.value.copy(passwd = intent.value, passwdError = null)
                        }
                        _newState.value = _newState.value.copy()
                    }
                }
            }
        }
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val sdata = repository.getSettings()
            _settings.value = sdata.map {
                SettingsDataUI(it)
            }
        }
    }
}

data class SettingsState(
    val name: String = "",
    val nameError: String? = null,

    val ip: String = "",
    val ipError: String? = null,

    val port: String = "",
    val portError: String? = null,

    val user: String = "",
    val userError: String? = null,

    val passwd: String = "",
    val passwdError: String? = null
)