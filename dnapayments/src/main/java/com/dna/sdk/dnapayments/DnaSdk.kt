package com.dna.sdk.dnapayments

import androidx.annotation.Keep
import com.dna.sdk.dnapayments.di.networkModule
import com.dna.sdk.dnapayments.di.repositoryModule
import com.dna.sdk.dnapayments.models.network.Environment
import com.dna.sdk.dnapayments.models.network.MerchantCredentials
import com.dna.sdk.dnapayments.utils.DnaConstants
import com.dna.sdk.dnapayments.utils.MyKoinContext
import org.koin.dsl.koinApplication

@Keep
class DnaSdk(credentials: MerchantCredentials) {

    init {
        DnaSdkCredentials.clientId = credentials.clientId
        DnaSdkCredentials.clientSecret = credentials.clientSecret
        DnaSdkCredentials.terminalId = credentials.terminalId
        DnaSdkCredentials.environment = credentials.environment
        DnaSdkCredentials.isLogging = credentials.loggingEnabled

        if (credentials.environment == Environment.DEV) {
            DnaSdkCredentials.webSocketUrl = DnaConstants.DEV_WEB_SOCKET_URL
            DnaSdkCredentials.authBaseUrl = DnaConstants.DEV_AUTH_BASE
            DnaSdkCredentials.baseUrl = DnaConstants.DEV_URL_BASE
        }
        val dnaSdkKoin = koinApplication {
            modules(networkModule)
            modules(repositoryModule)
        }

        MyKoinContext.koinApp = dnaSdkKoin
    }
}

internal object DnaSdkCredentials {
    var clientId: String = ""
    var clientSecret: String = ""
    var terminalId: String = ""
    var isLogging = false
    var environment: Environment = Environment.PROD
    var webSocketUrl = DnaConstants.PROD_WEB_SOCKET_URL
    var baseUrl = DnaConstants.PROD_URL_BASE
    var authBaseUrl = DnaConstants.PROD_AUTH_BASE
}