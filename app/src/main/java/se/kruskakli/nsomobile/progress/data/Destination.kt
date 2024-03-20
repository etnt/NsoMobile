package se.kruskakli.nsomobile.progress.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Destination(
    @SerialName("oper-data") val operData: JsonObject
)