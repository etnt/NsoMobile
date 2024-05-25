package se.kruskakli.nsomobile.debug.inet.domain


sealed interface NsoInetIntent {
    object ShowInet : NsoInetIntent
}