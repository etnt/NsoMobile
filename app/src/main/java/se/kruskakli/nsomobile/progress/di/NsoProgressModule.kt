package se.kruskakli.nsomobile.progress.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.nsopackage.domain.PackageViewModel
import se.kruskakli.nsomobile.progress.data.NsoProgressRepository
import se.kruskakli.nsomobile.progress.data.NsoProgressRepositoryImpl
import se.kruskakli.nsomobile.progress.domain.ProgressViewModel

val nsoProgressModule = module {
    single<NsoProgressRepository> { NsoProgressRepositoryImpl(get()) }
    viewModel { ProgressViewModel(get(), get(), get()) }
}