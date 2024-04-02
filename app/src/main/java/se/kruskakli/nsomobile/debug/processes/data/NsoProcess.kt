package se.kruskakli.nsomobile.debug.processes.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class NsoProcess(
    val ccall: String,
    val icall: String,
    val memory: String,
    val msgs: String,
    val name: String,
    val pid: String,
    val reds: String,
    @SerialName("shared-binaries") val sharedBinaries: String?
)