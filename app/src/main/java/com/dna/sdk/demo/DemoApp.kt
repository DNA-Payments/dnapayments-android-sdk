package com.dna.sdk.demo

import android.app.Application
import com.dna.sdk.demo.di.preferenceModule
import com.dna.sdk.dnapayments.DnaSdk
import com.dna.sdk.dnapayments.models.network.Environment
import com.dna.sdk.dnapayments.models.network.MerchantCredentials
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DemoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DnaSdk(
            MerchantCredentials(
                clientId = "Test Merchant",
                clientSecret = "PoF84JqIG8Smv5VpES9bcU31kmfSqLk8Jdo7",
                terminalId = "b553ffcc-7df4-47b1-887b-fa4108af9c85",
                environment = Environment.DEV,
                loggingEnabled = true
            )
        )

        startKoin {
            androidLogger()
            androidContext(this@DemoApp)
            modules(preferenceModule)
        }
    }
}