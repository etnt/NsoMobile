package se.kruskakli.nsomobile.debug.inet.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Inet(
    @SerialName("nso-dbg:inet") val nsoInet: NsoInet
)