package se.kruskakli.nsomobile.progress.data

data class Event(
    val context: String,
    val datastore: String,
    val message: String,
    val parent-span-id: String,
    val session-id: String,
    val span-id: String,
    val timer: String,
    val timestamp: String,
    val trace-id: String,
    val transaction-id: String
)