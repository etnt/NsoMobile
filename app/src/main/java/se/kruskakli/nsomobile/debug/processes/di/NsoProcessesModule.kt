package se.kruskakli.nsomobile.debug.processes.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.debug.processes.data.NsoProcessRepository
import se.kruskakli.nsomobile.debug.processes.data.NsoProcessRepositoryImpl
import se.kruskakli.nsomobile.debug.processes.domain.NsoProcessesViewModel


val nsoProcessesModule = module {
    single<NsoProcessRepository> { NsoProcessRepositoryImpl(get()) }
    viewModel { NsoProcessesViewModel(get(), get(), get()) }
}