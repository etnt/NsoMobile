package se.kruskakli.nso.data.alarms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Alarm(
    val device: String,
    @SerialName("is-cleared") val isCleared: Boolean,
    @SerialName("last-alarm-text") val lastAlarmText: String,
    @SerialName("last-perceived-severity") val lastPerceivedSeverity: String,
    @SerialName("last-status-change") val lastStatusChange: String,
    @SerialName("managed-object") val managedObject: String,
    @SerialName("specific-problem") val specificProblem: String,
    @SerialName("status-change") val statusChange: List<StatusChange>,
    val type: String
)