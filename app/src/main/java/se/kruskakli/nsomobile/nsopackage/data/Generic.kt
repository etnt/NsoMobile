package se.kruskakli.nsomobile.nsopackage.data

import kotlinx.serialization.Serializable

@Serializable
data class Generic(
    val javaClassName: String,
    val nedId: String
)