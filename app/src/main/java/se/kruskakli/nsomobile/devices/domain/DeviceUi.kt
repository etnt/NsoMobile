package se.kruskakli.nsomobile.devices.domain

data class DeviceUi(
    val name: String,
    val lastConnected: String,
    val address: String,
    val port: String,
    val authgroup: String,
    val commitQueue: CommitQueueUI,
    val state: StateUI,
    val alarmSummary: TailfNcsAlarmsAlarmSummaryUI
) {
    data class CommitQueueUI(
        val queueLength: String
    )

    data class StateUI(
        val operState: String,
        val transactionMode: String?,
        val adminState: String
    )

    data class TailfNcsAlarmsAlarmSummaryUI(
        val indeterminates: String,
        val critical: String,
        val major: String,
        val minor: String,
        val warning: String
    ) {
        fun sum(): Int {
            return indeterminates.toInt() + critical.toInt() + major.toInt() + minor.toInt() + warning.toInt()
        }
    }
}
