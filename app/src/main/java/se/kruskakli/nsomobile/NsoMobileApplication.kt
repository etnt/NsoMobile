package se.kruskakli.nsomobile

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import se.kruskakli.nsomobile.releasenote.di.releaseNoteModule


class NsoMobileApplication() : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NsoMobileApplication)
            modules(releaseNoteModule)
        }
    }


}