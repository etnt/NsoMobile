package se.kruskakli.nsomobile.syscounters.data

import kotlinx.coroutines.runBlocking
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
                "192.168.1.231",
                "9080",
                "admin",
                "admin"
            ).onSuccess {
                logger.info("Result JSON: $it")
            }.onFailure {
                logger.info("Error: $it")
            }
        }
        stopKoin()
    }
}