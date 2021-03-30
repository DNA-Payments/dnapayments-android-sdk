package com.dna.sdk.demo.di

import android.app.Application
import com.dna.sdk.demo.preferences.Preferences
import com.dna.sdk.demo.preferences.PreferencesImpl
import org.koin.dsl.module

val preferenceModule = module {
    single { providePreferences(get()) }
}

fun providePreferences(app: Application): Preferences {
    return PreferencesImpl(app)
}