package se.kruskakli.nsomobile.releasenote.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton


interface ReleaseNoteRepository {
    fun getReleaseNotes(): List<ReleaseNote>
}

@Singleton
class ReleaseNoteRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ReleaseNoteRepository {

    override fun getReleaseNotes(): List<ReleaseNote> {
        val jsonString = context.assets.open("release_notes.json").bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }
}


