package se.kruskakli.nsomobile.main.domain

interface MainIntent {
    data class EnterScreen(val page: TabPage) : MainIntent
}