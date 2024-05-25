package se.kruskakli.nsomobile.debug.inet.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class All(
    @SerialName("foreign_address") val foreignAddress: String,
    @SerialName("local_address") val localAddress: String,
    val module: String,
    val owner: String,
    val port: String,
    @SerialName("recv") val received: String,
    val sent: String,
    val state: String,
    val type: String?
)