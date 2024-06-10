package se.kruskakli.nsomobile

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import se.kruskakli.nsomobile.alarms.di.alarmsModule
import se.kruskakli.nsomobile.core.di.networkModule
import se.kruskakli.nsomobile.debug.allocators.di.nsoAllocatorsModule
import se.kruskakli.nsomobile.debug.ets.di.nsoEtsModule
import se.kruskakli.nsomobile.debug.inet.di.nsoInetModule
import se.kruskakli.nsomobile.debug.processes.di.nsoProcessesModule
import se.kruskakli.nsomobile.devices.di.nsoDevicesModule
import se.kruskakli.nsomobile.main.di.mainModule
import se.kruskakli.nsomobile.nsopackage.di.nsoPackageModule
import se.kruskakli.nsomobile.progress.di.nsoProgressModule
import se.kruskakli.nsomobile.releasenote.di.releaseNoteModule
import se.kruskakli.nsomobile.settings.di.settingsModule
import se.kruskakli.nsomobile.syscounters.di.sysCountersModule


class NsoMobileApplication() : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NsoMobileApplication)
            modules(
                mainModule,
                releaseNoteModule,
                settingsModule,
                networkModule,
                sysCountersModule,
                alarmsModule,
                nsoPackageModule,
                nsoDevicesModule,
                nsoProgressModule,
                nsoProcessesModule,
                nsoInetModule,
                nsoEtsModule,
                nsoAllocatorsModule
            )
        }
    }


}