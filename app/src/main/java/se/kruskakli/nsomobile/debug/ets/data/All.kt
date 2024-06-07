package se.kruskakli.nsomobile.debug.ets.data

import kotlinx.serialization.Serializable

@Serializable
data class All(
    val id: String,
    val mem: String,
    val name: String,
    val owner: String,
    val size: String,
    val type: String
)


