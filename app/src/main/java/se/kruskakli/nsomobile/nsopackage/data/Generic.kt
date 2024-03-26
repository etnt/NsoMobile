package se.kruskakli.nsomobile.nsopackage.data

import kotlinx.serialization.Serializable

@Serializable
data class Generic(
    val javaClassName: String? = null,
    val nedId: String? = null
)