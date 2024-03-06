package se.kruskakli.nsomobile.releasenote.data

import android.content.Context
import androidx.compose.ui.text.Paragraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlin.reflect.KClass

interface ReleaseNoteRepository {
    suspend fun getReleaseNotes(): List<ReleaseNote>
}

class ReleaseNoteRepositoryImpl(private val context: Context) : ReleaseNoteRepository {

    val json = Json {
        serializersModule = SerializersModule {
            polymorphic(TextPiece::class) {
                subclass(TextPiece.Paragraph::class, TextPiece.Paragraph.serializer())
                subclass(TextPiece.BulletList::class, TextPiece.BulletList.serializer())
            }
        }
        // This is the default and can be omitted unless you've changed it.
        classDiscriminator = "type"
    }

    override suspend fun getReleaseNotes(): List<ReleaseNote> {
        val jsonString = context.assets.open("release_notes.json").bufferedReader().use { it.readText() }
        return json.decodeFromString(jsonString)

    }

}