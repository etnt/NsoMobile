package se.kruskakli.nsomobile.alarms.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.alarms.data.AlarmsRepository
import se.kruskakli.nsomobile.alarms.data.AlarmsRepositoryImpl
import se.kruskakli.nsomobile.alarms.domain.AlarmsViewModel

val alarmsModule = module {
    single<AlarmsRepository> { AlarmsRepositoryImpl(get()) }
    viewModel { AlarmsViewModel(get(), get(), get()) }
}