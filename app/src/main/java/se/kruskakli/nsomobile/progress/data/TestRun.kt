package se.kruskakli.nsomobile.progress.data

import kotlinx.coroutines.runBlocking
import okhttp3.internal.concurrent.TaskRunner.Companion.logger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import se.kruskakli.nsomobile.core.di.networkModule
import se.kruskakli.nsomobile.progress.di.nsoProgressModule


object TestRun : KoinComponent {
    private val nsoProgressRepository: NsoProgressRepository by inject()

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin {
            modules(networkModule, nsoProgressModule)
        }

        runBlocking {
            nsoProgressRepository.getNsoProgress (
                "10.227.64.81",
                //"192.168.1.231",
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