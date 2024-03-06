package se.kruskakli.nsomobile.releasenote.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
Example JSON of a list of release notes:
[
 {
   "version": "0.1.0",
   "date": "2021-10-01",
   "textPieces": [
     {
       "type": "Paragraph",
       "text": "This is a release note for the new version of the app."
     },
     {
       "type": "BulletList",
       "items": [
         "New feature: Dark mode",
         "Bug fix: Crash on startup"
       ]
     }
   ]
 }
]

*/

@Serializable
data class ReleaseNote(
    val version: String,
    val date: String,
    val textPieces: List<TextPiece>
)

@Serializable
sealed class TextPiece {
    @Serializable @SerialName("Paragraph")
    data class Paragraph(val text: String) : TextPiece()
    @Serializable @SerialName("BulletList")
    data class BulletList(val items: List<String>) : TextPiece()
}
