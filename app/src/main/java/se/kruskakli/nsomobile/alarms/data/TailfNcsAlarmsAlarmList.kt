package se.kruskakli.nsomobile.alarms.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TailfNcsAlarmsAlarmList(
    val alarm: List<Alarm>,
    @SerialName("last-changed") val lastChanged: String,
    @SerialName("number-of-alarms") val numberOfAlarms: Int
)