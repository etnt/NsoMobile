package se.kruskakli.nsomobile.main.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.main.domain.EventChannel
import se.kruskakli.nsomobile.main.domain.MainViewModel


val mainModule = module {
    single { EventChannel }
    viewModel { MainViewModel() }
}