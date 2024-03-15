package se.kruskakli.nsomobile.alarms.domain

import se.kruskakli.nsomobile.alarms.data.Alarm
import se.kruskakli.nsomobile.alarms.data.StatusChange

fun Alarm.toAlarmUi(): AlarmUi {
    return AlarmUi(
        device = device,
        isCleared = isCleared.toString(),
        lastAlarmText = lastAlarmText,
        lastPerceivedSeverity = lastPerceivedSeverity,
        lastStatusChange = lastStatusChange,
        managedObject = managedObject,
        specificProblem = specificProblem,
        statusChange = statusChange.map { it.toStatusChangeUi() },
        type = type
    )
}

fun StatusChange.toStatusChangeUi(): AlarmUi.StatusChange {
    return AlarmUi.StatusChange(
        alarmText = alarmText,
        eventTime = eventTime,
        perceivedSeverity = perceivedSeverity,
        receivedTime = receivedTime
    )
}
