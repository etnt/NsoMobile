package se.kruskakli.nsomobile.debug.inet.domain

data class InetUi(
    val foreignAddress: String,
    val localAddress: String,
    val module: String,
    val owner: String,
    val port: String,
    val received: String,
    val sent: String,
    val state: String,
    val type: String?
)
