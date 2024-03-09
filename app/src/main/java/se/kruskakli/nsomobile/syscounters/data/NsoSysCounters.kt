package se.kruskakli.nsomobile.syscounters.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NsoSysCounters(
    @SerialName("tailf-ncs:counter") val sysCounters: SysCounters? = null
)