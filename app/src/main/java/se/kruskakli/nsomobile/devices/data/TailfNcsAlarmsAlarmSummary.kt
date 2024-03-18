package se.kruskakli.nsomobile.devices.data

import kotlinx.serialization.Serializable

@Serializable
data class TailfNcsAlarmsAlarmSummary(
    val indeterminates: Int,
    val criticals: Int,
    val majors: Int,
    val minors: Int,
    val warnings: Int
)