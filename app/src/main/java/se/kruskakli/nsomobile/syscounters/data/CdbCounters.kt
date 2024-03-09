package se.kruskakli.nsomobile.syscounters.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CdbCounters(
    val compactions: Long? = null,
    @SerialName("compaction") val compaction: Compaction? = null,
    @SerialName("boot-time") val bootTime: String? = null,
    @SerialName("phase0-time") val phase0Time: String? = null,
    @SerialName("phase1-time") val phase1Time: String? = null,
    @SerialName("phase2-time") val phase2Time: String? = null,
) {
    @Serializable
    data class Compaction(
        @SerialName("A-cdb") val aCdb: Long? = null,
        @SerialName("O-cdb") val oCdb: Long? = null,
        @SerialName("S-cdb") val sCdb: Long? = null,
        val total: Long? = null
    )
}