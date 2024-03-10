package se.kruskakli.nsomobile

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import se.kruskakli.nsomobile.core.di.networkModule
import se.kruskakli.nsomobile.releasenote.di.releaseNoteModule
import se.kruskakli.nsomobile.settings.di.settingsModule
import se.kruskakli.nsomobile.syscounters.di.sysCountersModule


class NsoMobileApplication() : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NsoMobileApplication)
            modules(releaseNoteModule, settingsModule, networkModule, sysCountersModule)
        }
    }


}