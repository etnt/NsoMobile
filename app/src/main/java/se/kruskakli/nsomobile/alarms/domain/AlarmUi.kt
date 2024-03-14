package se.kruskakli.nsomobile.alarms.domain

data class AlarmUi(
    val device: String,
    val isCleared: String,
    val lastAlarmText: String,
    val lastPerceivedSeverity: String,
    val lastStatusChange: String,
    val managedObject: String,
    val specificProblem: String,
    val statusChange: List<StatusChange>,
    val type: String
) {
    data class StatusChange(
        val alarmText: String?,
        val eventTime: String,
        val perceivedSeverity: String,
        val receivedTime: String
    )
}
