package se.kruskakli.nsomobile.progress.domain

import kotlinx.serialization.SerialName

data class ProgressUi (
    val trace: List<ProgressTrace>
) {

    data class ProgressTrace(
        val name: String,
        val events: List<ProgressEvent>
    )
    data class ProgressEvent(
        val context: String,
        val datastore: String,
        val message: String,
        val parentSpanId: String,
        val sessionId: String,
        val spanId: String,
        val timer: String,
        val timestamp: String,   // Consider using a proper date/time type
        val traceId: String,
        val transactionId: String,

        // Used to hold any events that are direct children of the event in question
        val children: MutableList<ProgressEvent> = mutableListOf()
    )
}
