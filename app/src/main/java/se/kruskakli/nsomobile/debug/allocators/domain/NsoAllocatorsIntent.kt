package se.kruskakli.nsomobile.debug.allocators.domain



sealed interface NsoAllocatorsIntent {
    object ShowAllocators : NsoAllocatorsIntent
}