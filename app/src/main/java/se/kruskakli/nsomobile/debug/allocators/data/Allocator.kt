package se.kruskakli.nsomobile.debug.allocators.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Allocator(
    val name: String,
    val instance: Int,
    @SerialName("blocks-size") val blocksSize: String,
    @SerialName("carriers-size") val carriersSize: String,
    val utilization: String,
    val mbcs: SizeInfo,
    val sbcs: SizeInfo
)
