package se.kruskakli.nsomobile.syscounters.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.core.data.NetworkRepository
import se.kruskakli.nsomobile.core.data.NetworkRepositoryImpl
import se.kruskakli.nsomobile.settings.domain.SettingsViewModel
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepository
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepositoryImpl

val sysCountersModule = module {
    single<NetworkRepository> { NetworkRepositoryImpl() }
    single<SysCountersRepository> { SysCountersRepositoryImpl(get()) }
    //single { SysCountersRepository(get()) }
    viewModel { SettingsViewModel(get()) }
}