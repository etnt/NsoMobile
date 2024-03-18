package se.kruskakli.nsomobile.devices.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CommitQueue(
    @SerialName("queue-length") val queueLength: Int
)