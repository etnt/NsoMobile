package se.kruskakli.nsomobile.debug.processes.domain

data class ProcessUi(
    val ccall: String,
    val icall: String,
    val memory: String,
    val msgs: String,
    val name: String,
    val pid: String,
    val reds: String,
    val sharedBinaries: String?
)
