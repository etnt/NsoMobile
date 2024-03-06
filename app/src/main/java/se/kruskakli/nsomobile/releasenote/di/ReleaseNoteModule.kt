package se.kruskakli.nsomobile.releasenote.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import se.kruskakli.nsomobile.releasenote.data.ReleaseNoteRepository
import se.kruskakli.nsomobile.releasenote.data.ReleaseNoteRepositoryImpl
import se.kruskakli.nsomobile.releasenote.domain.ReleaseNoteViewModel

val releaseNoteModule = module {
    single<ReleaseNoteRepository> { ReleaseNoteRepositoryImpl(androidContext()) }
    viewModel { ReleaseNoteViewModel(get()) }
}
