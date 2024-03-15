package se.kruskakli.nsomobile.nsopackage.data

import kotlinx.serialization.Serializable

@Serializable
data class Component(
    val name: String,
    val ned: Ned? = null
)