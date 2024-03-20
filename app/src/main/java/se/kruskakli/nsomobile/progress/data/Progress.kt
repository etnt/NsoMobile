package se.kruskakli.nsomobile.progress.data

import kotlinx.serialization.Serializable

@Serializable
data class Progress(
    val trace: List<Trace>
)