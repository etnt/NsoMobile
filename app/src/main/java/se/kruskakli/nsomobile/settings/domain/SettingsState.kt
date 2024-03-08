package se.kruskakli.nsomobile.settings.domain

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