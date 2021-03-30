package com.dna.sdk.dnapayments.models.network

import androidx.annotation.Keep

@Keep
data class MerchantCredentials(
    val clientId: String,
    val clientSecret: String,
    val terminalId: String,
    val environment: Environment,
    val loggingEnabled: Boolean = false
)

@Keep
enum class Environment {
    PROD, DEV
}
