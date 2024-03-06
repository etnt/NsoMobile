package se.kruskakli.nsomobile.settings.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.settings.data.SettingsData
import se.kruskakli.nsomobile.settings.data.SettingsRepository

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _settings = MutableStateFlow<List<SettingsData>>(emptyList())
    val settings: StateFlow<List<SettingsData>> = _settings.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val sdata = repository.getSettings()
            _settings.value = sdata
        }
    }
}

