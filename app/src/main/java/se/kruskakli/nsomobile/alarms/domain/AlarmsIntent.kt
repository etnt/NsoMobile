package se.kruskakli.nsomobile.alarms.domain

sealed interface AlarmsIntent {
    object ShowAlarms : AlarmsIntent
}