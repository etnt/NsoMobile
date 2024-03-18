package se.kruskakli.nsomobile.devices.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.devices.data.NsoDevicesRepository
import se.kruskakli.nsomobile.devices.data.NsoDevicesRepositoryImpl
import se.kruskakli.nsomobile.devices.domain.DevicesViewModel

val nsoDevicesModule = module {
    single<NsoDevicesRepository> { NsoDevicesRepositoryImpl(get()) }
    viewModel { DevicesViewModel(get(), get(), get()) }
}