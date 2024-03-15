package se.kruskakli.nsomobile.nsopackage.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.alarms.data.AlarmsRepository
import se.kruskakli.nsomobile.alarms.data.AlarmsRepositoryImpl
import se.kruskakli.nsomobile.alarms.domain.AlarmsViewModel
import se.kruskakli.nsomobile.nsopackage.data.NsoPackageRepository
import se.kruskakli.nsomobile.nsopackage.data.NsoPackageRepositoryImpl
import se.kruskakli.nsomobile.nsopackage.domain.PackageViewModel

val nsoPackageModule = module {
    single<NsoPackageRepository> { NsoPackageRepositoryImpl(get()) }
    viewModel { PackageViewModel(get(), get(), get()) }
}