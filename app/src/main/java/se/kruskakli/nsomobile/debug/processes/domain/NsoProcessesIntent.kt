package se.kruskakli.nsomobile.debug.processes.domain

import se.kruskakli.nsomobile.core.presentation.SortType

sealed interface NsoProcessesIntent {
    object ShowProcesses : NsoProcessesIntent
    data class SortData(val field: String, val sortType: SortType) : NsoProcessesIntent
}