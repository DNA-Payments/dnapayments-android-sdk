package com.dna.sdk.demo

import android.app.Application
import com.dna.sdk.demo.di.preferenceModule
import com.dna.sdk.demo.preferences.Preferences
import com.dna.sdk.dnapayments.DnaSdk
import com.dna.sdk.dnapayments.models.network.Environment
import com.dna.sdk.dnapayments.models.network.MerchantCredentials
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DemoApp : Application() {
    private val preferences: Preferences by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DemoApp)
            modules(preferenceModule)
        }

        setupDnaSdk()
    }

    fun setupDnaSdk() {
        if (preferences.getTerminalId().isBlank()) {
            val defaultTerminals = resources.getStringArray(R.array.terminal_array)
            preferences.setTerminalId(defaultTerminals[0])
        }

        DnaSdk(
            MerchantCredentials(
                clientId = "Test Merchant",
                clientSecret = "PoF84JqIG8Smv5VpES9bcU31kmfSqLk8Jdo7",
                terminalId = preferences.getTerminalId(),
                environment = Environment.DEV,
                loggingEnabled = true
            )
        )
    }
}