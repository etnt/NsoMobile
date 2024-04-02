package se.kruskakli.nsomobile.debug.processes.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NsoProcesses(
    @SerialName("nso-dbg:all") val nsoAllProcesses: List<NsoProcess>
)