package se.kruskakli.nsomobile.devices.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


// Top node of the returned JSON from the NSO Restconf API
@Serializable
data class NsoDevices(
    @SerialName("tailf-ncs:devices") val nsoDevices: Devices
)