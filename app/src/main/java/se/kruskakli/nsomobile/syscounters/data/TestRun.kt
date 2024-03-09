package se.kruskakli.nsomobile.syscounters.data

import android.util.Log
import io.ktor.client.call.body
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
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
            val result = sysCountersRepository.getSysCounters()
            val body =result.body<String>()
            logger.info("Result BODY: $body")
            val sysCounters = Json.decodeFromString<NsoSysCounters>(body)
            logger.info("Result JSON: $sysCounters")
        }
        stopKoin()
    }
}