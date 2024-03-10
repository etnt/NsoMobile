package se.kruskakli.nsomobile.settings.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.settings.data.SettingsRepository
import se.kruskakli.nsomobile.settings.data.SettingsRepositoryImpl
import se.kruskakli.nsomobile.settings.domain.SettingsViewModel
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepository
import se.kruskakli.nsomobile.settings.domain.SystemInfoRepositoryImpl

val settingsModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }
    single<SystemInfoRepository> { SystemInfoRepositoryImpl() }

    viewModel { SettingsViewModel(get(), get()) }
}