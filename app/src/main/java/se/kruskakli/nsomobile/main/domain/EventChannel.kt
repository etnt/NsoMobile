package se.kruskakli.nsomobile.main.domain

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow


// An event channel that will be used to broadcast refresh events.

object EventChannel {
    private val _refreshChannel = Channel<TabPage>(Channel.CONFLATED)
    val refreshFlow: Flow<TabPage> = _refreshChannel.receiveAsFlow()

    suspend fun triggerRefresh(screen: TabPage) {
        _refreshChannel.send(screen)
    }
}