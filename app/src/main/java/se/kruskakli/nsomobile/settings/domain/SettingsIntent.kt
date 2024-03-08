package se.kruskakli.nsomobile.settings.domain

sealed interface SettingsIntent {
    data class SetFieldValue(val field: String, val value: String) : SettingsIntent

}