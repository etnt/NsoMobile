package se.kruskakli.nsomobile.syscounters.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepository
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepositoryImpl

val sysCountersModule = module {
    single<SysCountersRepository> { SysCountersRepositoryImpl() }
    //viewModel { SettingsViewModel(get()) }
}