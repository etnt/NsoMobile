package se.kruskakli.nsomobile.releasenote.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

interface ReleaseNoteRepository {
    suspend fun getReleaseNotes(): List<ReleaseNote>
}

class ReleaseNoteRepositoryImpl(private val context: Context) : ReleaseNoteRepository {
    override suspend fun getReleaseNotes(): List<ReleaseNote> {
        val json = withContext(Dispatchers.IO) {
            context.assets.open("release_notes.json").bufferedReader().use { it.readText() }
        }
        return Json { ignoreUnknownKeys = true }.decodeFromString(json)
    }
}