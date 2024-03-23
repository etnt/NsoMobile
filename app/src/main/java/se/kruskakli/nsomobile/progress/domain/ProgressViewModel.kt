package se.kruskakli.nsomobile.progress.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import se.kruskakli.nsomobile.core.domain.DataState
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.TabPage
import se.kruskakli.nsomobile.nsopackage.domain.PackageIntent
import se.kruskakli.nsomobile.progress.data.NsoProgressRepository
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

class ProgressViewModel (
    private val progressRepository: NsoProgressRepository,
    private val systemInfoRepository: SystemInfoRepository,
    private val eventChannel: EventChannel
    ): ViewModel() {

    private val _progress = MutableStateFlow<DataState<ProgressUi>>(DataState.Idle)
    val progress = _progress.asStateFlow()

    private val _progressTree = MutableStateFlow<DataState<MutableMap<String, MutableList<ProgressUi.ProgressEvent>>>>(DataState.Idle)
    val progressTree = _progressTree.asStateFlow()

    init {
        // We only listen for the refresh event that is relevant to us!
        viewModelScope.launch {
            EventChannel.refreshFlow
                .filter { it == TabPage.Progress } // Listen only to relevant screen refreshes
                .collect {
                    refreshContent()
                }
        }
    }

    fun handleIntent(intent: ProgressIntent) {
        when (intent) {
            is ProgressIntent.showProgress -> {
                getProgress()
            }
        }
    }

    private fun refreshContent() {
        getProgress()
    }

    fun getProgress() {
        DataState.Loading.also { _progress.value = it }
        val systemInfo = systemInfoRepository.getSystemInfo()
        if (systemInfo != null) {
            viewModelScope.launch {
                progressRepository.getNsoProgress(
                    systemInfo.ip,
                    systemInfo.port,
                    systemInfo.user,
                    systemInfo.password
                ).onSuccess { nsoProgress ->
                    val progressTraces = nsoProgress.nsoProgress.toProgressUi()
                    val progressTree = mutableMapOf<String, MutableList<ProgressUi.ProgressEvent>>()
                    for (progressTrace in progressTraces.trace) {
                        val name = progressTrace.name
                        val treeEvents: MutableList<ProgressUi.ProgressEvent> = mutableListOf()
                        val eventsByOperation =
                            progressTrace.events.groupBy { it.traceId + "_" + it.transactionId }
                        for ((operationId, events) in eventsByOperation) {
                            // Build tree structure based on parentSpanId and spanId
                            //Log.d("ProgressViewModel", "getProgress: $operationId - ${events}")
                            val rootEvents = buildTreeStructure(events)
                            rootEvents.forEach { treeEvents.add(it) }
                            //Log.d("ProgressViewModel", "getProgress 2: $operationId - ${rootEvents}")

                            // Calculate elapsed time and display messages
                            displayOperationDetails(rootEvents)
                        }
                        progressTree.put(name, treeEvents)
                    }
                    DataState.Success(progressTree).also { newState ->
                        Log.d("ProgressViewModel", "ProgressTree: $progressTree")
                        _progressTree.value = newState
                    }
                    DataState.Success(nsoProgress.nsoProgress.toProgressUi()).also { it ->
                        Log.d("ProgressViewModel", "getProgress: ${it.getSuccesData()}")
                        _progress.value = it
                    }
                }.onFailure {
                    DataState.Failure(it).also { newState ->
                        Log.d("ProgressViewModel", "getProgress: ${newState.getFailureMessage()}")
                        _progress.value = newState
                    }
                }
            }
        }
    }

    private fun buildTreeStructure(events: List<ProgressUi.ProgressEvent>): List<ProgressUi.ProgressEvent> {
        val eventMap = events.associateBy { it.spanId }
        val roots = mutableListOf<ProgressUi.ProgressEvent>()

        for (event in events) {
            if (event.parentSpanId.isNullOrEmpty()) {
                roots.add(event)
            } else {
                eventMap[event.parentSpanId]?.children?.add(event)
            }
        }

        return roots
    }

    fun parseTimestamp(timestamp: String): OffsetDateTime {
        // Ensure the fractional part is exactly six digits by padding or truncating as necessary
        val normalizedTimestamp = timestamp.replace(Regex("(\\.\\d{1,6})")) { matchResult ->
            matchResult.value.padEnd(7, '0') // Pad the fractional part (including the dot) to 7 characters
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSxxx")
        return OffsetDateTime.parse(normalizedTimestamp, formatter)
    }

    fun calculateElapsedTime(start: OffsetDateTime, end: OffsetDateTime): Double {
        // Calculate the duration between the two OffsetDateTime instances
        val duration = Duration.between(start, end)
        // Convert the duration to milliseconds
        val durationMillis = BigDecimal(duration.toMillis())
        // Divide by 1000.0 to convert to seconds, rounding to 3 decimal places
        val durationSeconds = durationMillis.divide(BigDecimal(1000), 3, RoundingMode.HALF_UP)
        // Return the result as a double
        return durationSeconds.toDouble()
    }

    private fun displayOperationDetails(rootEvents: List<ProgressUi.ProgressEvent>, level: Int = 0) {
        for (event in rootEvents) {
            // Determine the padding for hierarchical display
            val padding = " ".repeat(level * 4)

            // Calculate the elapsed time if this event has children
            val elapsedTime = if (event.children.isNotEmpty()) {
                val start = parseTimestamp(event.timestamp)
                val end = parseTimestamp(event.children.maxOf { it.timestamp })
                calculateElapsedTime(start, end)
            } else {
                0.0 // No children means no elapsed time
            }

            // Display the current event's details
            Log.d("displayOperationsDetails","$padding- ${event.message} [Elapsed Time: $elapsedTime s] span-id(${event.spanId}) trace-id(${event.traceId}) transaction-id(${event.transactionId})")

            // Recursively display child events, if any
            displayOperationDetails(event.children, level + 1)
        }
    }
}