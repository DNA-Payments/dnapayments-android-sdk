package com.dna.sdk.dnapayments.api.client

import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.api.service.AuthService
import com.dna.sdk.dnapayments.models.network.AuthToken
import com.dna.sdk.dnapayments.utils.Logger

internal class AuthClient(private val service: AuthService) {

    suspend fun getUserToken(
        grantType: String,
        scope: String,
        clientId: String,
        clientSecret: String,
        invoiceId: String,
        amount: Double,
        currency: String,
        terminal: String,
        paymentFormUrl: String,
        source: String
    ): ApiResponse<AuthToken> {
        Logger.logAuthUserToken(
            grantType,
            scope,
            clientId,
            clientSecret,
            invoiceId,
            amount,
            currency,
            terminal,
            paymentFormUrl,
            source
        )

        val response = ApiResponse.create(
            service.getUserToken(
                grantType,
                scope,
                clientId,
                clientSecret,
                invoiceId,
                amount,
                currency,
                terminal,
                paymentFormUrl,
                source
            )
        )
        Logger.logResponse(response)

        return response
    }

    suspend fun getOperationsToken(
        grantType: String,
        scope: String,
        clientId: String,
        clientSecret: String,
        source: String
    ): ApiResponse<AuthToken> {
        Logger.logAuthOperations(
            grantType, scope, clientId, clientSecret, source
        )
        val response = ApiResponse.create(
            service.getOperationsToken(grantType, scope, clientId, clientSecret, source)
        )

        Logger.logResponse(response)

        return response
    }
}