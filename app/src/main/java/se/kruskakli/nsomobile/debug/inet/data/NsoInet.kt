package se.kruskakli.nsomobile.debug.inet.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NsoInet(
    val all: List<All>
)