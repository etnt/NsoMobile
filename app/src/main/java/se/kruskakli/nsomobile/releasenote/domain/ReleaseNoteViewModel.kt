package se.kruskakli.nsomobile.releasenote.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.releasenote.data.ReleaseNote
import se.kruskakli.nsomobile.releasenote.data.ReleaseNoteRepository
import javax.inject.Inject


@HiltViewModel
class ReleaseNoteViewModel @Inject constructor(
    val repository: ReleaseNoteRepository
) : ViewModel() {

    private val _releaseNotes = MutableStateFlow<List<ReleaseNote>>(emptyList())
    val releaseNotes: StateFlow<List<ReleaseNote>> = _releaseNotes.asStateFlow()

    init {
        loadReleaseNotes()
    }

    private fun loadReleaseNotes() {
        viewModelScope.launch {
            val notes = repository.getReleaseNotes()
            _releaseNotes.value = notes
        }
    }
}
