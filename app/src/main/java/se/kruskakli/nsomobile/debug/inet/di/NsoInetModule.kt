package se.kruskakli.nsomobile.debug.inet.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.debug.inet.data.NsoInetRepository
import se.kruskakli.nsomobile.debug.inet.data.NsoInetRepositoryImpl
import se.kruskakli.nsomobile.debug.inet.domain.NsoInetViewModel


val nsoInetModule = module {
    single<NsoInetRepository> { NsoInetRepositoryImpl(get()) }
    viewModel { NsoInetViewModel(get(), get(), get()) }
}