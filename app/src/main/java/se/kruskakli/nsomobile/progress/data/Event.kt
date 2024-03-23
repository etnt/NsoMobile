package se.kruskakli.nsomobile.progress.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val context: String,
    val datastore: String,
    val message: String,
    val duration: String? = null,
    @SerialName("parent-span-id") val parentSpanId: String? = null,
    @SerialName("session-id") val sessionId: String,
    @SerialName("span-id") val spanId: String,
    val timer: String,
    val timestamp: String,
    @SerialName("trace-id") val traceId: String,
    @SerialName("transaction-id") val transactionId: String
)