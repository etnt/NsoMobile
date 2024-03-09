package se.kruskakli.nsomobile.syscounters.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DeviceCounters(
    val connect: Long?,
    @SerialName("connect-failed") val connectFailed: Long?,
    @SerialName("sync-from") val syncFrom: Long?,
    @SerialName("sync-to") val syncTo: Long?,
    @SerialName("out-of-sync") val outOfSync: Long?
) 

