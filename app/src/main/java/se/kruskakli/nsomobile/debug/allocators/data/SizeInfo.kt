package se.kruskakli.nsomobile.debug.allocators.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SizeInfo(
    @SerialName("blocks-size") val blocksSize: String,
    @SerialName("carriers-size") val carriersSize: String,
    val utilization: String
)