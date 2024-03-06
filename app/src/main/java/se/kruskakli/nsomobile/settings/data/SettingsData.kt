package se.kruskakli.nsomobile.settings.data

import kotlinx.serialization.Serializable


@Serializable
data class SettingsData(
    val name: String,
    val ip: String,
    val port: String,
    val user: String,
    val passwd: String
)
