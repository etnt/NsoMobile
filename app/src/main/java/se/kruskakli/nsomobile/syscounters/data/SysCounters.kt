package se.kruskakli.nsomobile.syscounters.data

import kotlinx.serialization.Serializable


@Serializable
data class SysCounters(
    val transaction: Transaction? = null,
    val serviceConflicts: ServiceConflicts? = null,
    val cdb: CdbCounters? = null,
    val device: DeviceCounters? = null,
    val session: SessionCounters? = null
)