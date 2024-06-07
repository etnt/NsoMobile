package se.kruskakli.nsomobile.debug.ets.domain

import se.kruskakli.nsomobile.core.presentation.SortType


sealed interface NsoEtsIntent {
    object ShowEts : NsoEtsIntent

    data class SortData(val field: String, val sortType: SortType) : NsoEtsIntent
}
