package se.kruskakli.nsomobile.syscounters.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.core.data.NetworkRepository
import se.kruskakli.nsomobile.core.data.NetworkRepositoryImpl
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepositoryImpl
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepository
import se.kruskakli.nsomobile.syscounters.data.SysCountersRepositoryImpl
import se.kruskakli.nsomobile.syscounters.domain.SysCountersViewModel

val sysCountersModule = module {
    single<NetworkRepository> { NetworkRepositoryImpl() }
    single<SystemInfoRepository> { SystemInfoRepositoryImpl() }
    single<SysCountersRepository> { SysCountersRepositoryImpl(get()) }
    //single { SysCountersRepository(get()) }
    viewModel { SysCountersViewModel(get(), get()) }
}