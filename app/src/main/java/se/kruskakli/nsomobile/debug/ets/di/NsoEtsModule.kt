package se.kruskakli.nsomobile.debug.ets.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.debug.ets.data.NsoEtsRepository
import se.kruskakli.nsomobile.debug.ets.data.NsoEtsRepositoryImpl
import se.kruskakli.nsomobile.debug.ets.domain.NsoEtsViewModel


val nsoEtsModule = module {
    single<NsoEtsRepository> { NsoEtsRepositoryImpl(get()) }
    viewModel { NsoEtsViewModel(get(), get(), get()) }
}