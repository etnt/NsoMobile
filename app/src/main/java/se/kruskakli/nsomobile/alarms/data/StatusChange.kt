package se.kruskakli.nso.data.alarms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StatusChange(
    @SerialName("alarm-text") val alarmText: String? = null,
    @SerialName("event-time") val eventTime: String,
    @SerialName("perceived-severity") val perceivedSeverity: String,
    @SerialName("received-time") val receivedTime: String
)