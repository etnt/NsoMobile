package se.kruskakli.nsomobile.releasenote.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.releasenote.data.ReleaseNote
import se.kruskakli.nsomobile.releasenote.data.ReleaseNoteRepository

class ReleaseNoteViewModel(
    private val repository: ReleaseNoteRepository
) : ViewModel() {

    private val _releaseNotes = MutableStateFlow<List<ReleaseNote>>(emptyList())
    val releaseNotes: StateFlow<List<ReleaseNote>> = _releaseNotes.asStateFlow()

    init {
        Log.d("ReleaseNoteViewModel", "BEFORE loadReleaseNotes()")
        loadReleaseNotes()
    }

    private fun loadReleaseNotes() {
        viewModelScope.launch {
            val notes = repository.getReleaseNotes()
            Log.d("ReleaseNoteViewModel", "ReleaseNotes: $notes")
            _releaseNotes.value = notes
        }
    }
}
