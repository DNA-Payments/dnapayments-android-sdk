package com.dna.sdk.dnapayments.repository

import com.dna.sdk.dnapayments.DnaSdkCredentials
import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.api.client.AuthClient
import com.dna.sdk.dnapayments.models.network.AuthToken
import com.dna.sdk.dnapayments.utils.CustomKoinComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject

internal class AuthRepository : Repository, CustomKoinComponent() {
    private val authClient: AuthClient by inject()
    private val clientId = DnaSdkCredentials.clientId
    private val clientSecret = DnaSdkCredentials.clientSecret
    private val terminalId = DnaSdkCredentials.terminalId

    companion object {
        private var INSTANCE: AuthRepository? = null
        fun getInstance(): AuthRepository {
            if (INSTANCE == null) {
                INSTANCE = AuthRepository()
            }
            return INSTANCE!!
        }
    }

    internal suspend fun getUserToken(
        grantType: String,
        scope: String,
        invoiceId: String,
        amount: Double,
        currency: String,
        paymentFormUrl: String
    ): ApiResponse<AuthToken> {
        return withContext(Dispatchers.IO) {
            authClient.getUserToken(
                grantType,
                scope,
                clientId,
                clientSecret,
                invoiceId,
                amount,
                currency,
                terminalId,
                paymentFormUrl,
                "sdk_android"
            )
        }
    }

    internal suspend fun getOperationsToken(
        grantType: String,
        scope: String
    ): ApiResponse<AuthToken> {
        return withContext(Dispatchers.IO) {
            authClient.getOperationsToken(
                grantType,
                scope,
                clientId,
                clientSecret,
                "sdk_android"
            )
        }
    }
}