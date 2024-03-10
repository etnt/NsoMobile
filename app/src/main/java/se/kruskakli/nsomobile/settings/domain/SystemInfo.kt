package se.kruskakli.nsomobile.settings.domain

data class SystemInfo(
    val name: String,
    val ip: String,
    val port: String,
    val user: String,
    val password: String
)
