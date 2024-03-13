package se.kruskakli.nsomobile.settings.domain

sealed interface SettingsIntent {
    data class SetFieldValue(val field: String, val value: String) : SettingsIntent
    object SaveSettings : SettingsIntent
    data class RemoveSetting(val name: String) : SettingsIntent
    data class SetCurrentSystemInfo(val systemName: String) : SettingsIntent

}