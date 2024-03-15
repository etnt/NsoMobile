package se.kruskakli.nsomobile.nsopackage.data

import kotlinx.serialization.Serializable

@Serializable
data class Ned(
    val device: Device? = null,
    val generic: Generic? = null
)