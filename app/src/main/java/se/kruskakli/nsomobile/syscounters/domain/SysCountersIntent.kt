package se.kruskakli.nsomobile.syscounters.domain

sealed interface SysCountersIntent {
    object ShowSysCounters : SysCountersIntent
}
