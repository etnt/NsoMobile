package se.kruskakli.nsomobile.devices.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Devices(
    @SerialName("device") val devices: List<NcsDevice>
)
