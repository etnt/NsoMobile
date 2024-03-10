package se.kruskakli.nsomobile.syscounters.data

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import se.kruskakli.nsomobile.core.di.networkModule
import se.kruskakli.nsomobile.syscounters.di.sysCountersModule

object TestRun : KoinComponent {
    private val sysCountersRepository: SysCountersRepository by inject()

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin {
            modules(networkModule, sysCountersModule)
        }

        runBlocking {
            sysCountersRepository.getSysCounters (
                "192.168.1.147",
                "8080",
                "admin",
                "admin",
                onSuccess = { response: HttpResponse ->
                    val body = response.body<String>()
                    logger.info("Result BODY: $body")
                    val nsoSysCounters = Json.decodeFromString<NsoSysCounters>(body)
                    logger.info("Result JSON: $nsoSysCounters")
                    var sysCountersUI = nsoSysCounters.sysCounters?.toUiModel()
                    logger.info("Result UI model: $sysCountersUI")
                },
                onError = { error ->
                    logger.info("Error: $error")
                }
            )
        }
        stopKoin()
    }
}