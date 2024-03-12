package se.kruskakli.nsomobile.syscounters.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DeviceCounters(
    val connect: Long? = null,
    @SerialName("connect-failed") val connectFailed: Long? = null,
    @SerialName("sync-from") val syncFrom: Long? = null,
    @SerialName("sync-to") val syncTo: Long? = null,
    @SerialName("out-of-sync") val outOfSync: Long? = null
) 

