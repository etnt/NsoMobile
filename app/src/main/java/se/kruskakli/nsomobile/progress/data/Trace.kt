package se.kruskakli.nsomobile.progress.data

import kotlinx.serialization.Serializable

@Serializable
data class Trace(
    //val destination: Destination,
    val enabled: Boolean,
    val event: List<Event>,
    val name: String,
    val verbosity: String
)