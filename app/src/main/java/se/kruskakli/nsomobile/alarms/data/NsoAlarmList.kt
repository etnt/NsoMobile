package se.kruskakli.nsomobile.alarms.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NsoAlarmList(
    @SerialName("tailf-ncs-alarms:alarm-list") val nsoAlarmList: TailfNcsAlarmsAlarmList
)