package se.kruskakli.nsomobile.main.domain

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(
) : ViewModel() {

    private val _currentScreen = MutableStateFlow(TabPage.Home)
    val currentScreen: StateFlow<TabPage> = _currentScreen.asStateFlow()

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.EnterScreen ->
                _currentScreen.value = intent.page
        }
    }

}