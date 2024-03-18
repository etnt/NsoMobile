package se.kruskakli.nsomobile.devices.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NcsDevice(
    val name: String,
    @SerialName("last-connected") val lastConnected: String,
    val address: String,
    val port: Int,
    val authgroup: String,
    @SerialName("commit-queue") val commitQueue: CommitQueue,
    val state: State,
    @SerialName("tailf-ncs-alarms:alarm-summary") val alarmSummary: TailfNcsAlarmsAlarmSummary
)