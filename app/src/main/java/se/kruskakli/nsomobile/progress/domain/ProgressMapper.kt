package se.kruskakli.nsomobile.progress.domain

import se.kruskakli.nsomobile.progress.data.Event
import se.kruskakli.nsomobile.progress.data.Progress
import se.kruskakli.nsomobile.progress.data.Trace

fun Progress.toProgressUi(): ProgressUi {
    return ProgressUi(
        trace = trace.map { trace: Trace ->
            ProgressUi.ProgressTrace(
                name = trace.name,
                events = trace.event.map { event: Event ->
                    ProgressUi.ProgressEvent(
                        context = event.context,
                        datastore = event.datastore?.let { it } ?: "",
                        message = event.message,
                        duration = event.duration?.let { it } ?: "",
                        parentSpanId = event.parentSpanId?.let { it } ?: "",
                        sessionId = event.sessionId?.let { it } ?: "",
                        spanId = event.spanId,
                        timer = event.timer,
                        timestamp = event.timestamp,
                        traceId = event.traceId,
                        transactionId = event.transactionId?.let { it } ?: ""
                    )
                }
            )
        }
    )
}