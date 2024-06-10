package se.kruskakli.nsomobile.debug.allocators.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.debug.allocators.data.NsoAllocatorsRepository
import se.kruskakli.nsomobile.debug.allocators.data.NsoAllocatorsRepositoryImpl
import se.kruskakli.nsomobile.debug.allocators.domain.NsoAllocatorsViewModel


val nsoAllocatorsModule = module {
    single<NsoAllocatorsRepository> { NsoAllocatorsRepositoryImpl(get()) }
    viewModel { NsoAllocatorsViewModel(get(), get(), get()) }
}