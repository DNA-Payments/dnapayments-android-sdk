package com.dna.sdk.dnapayments.repository

import com.dna.sdk.dnapayments.DnaSdkCredentials
import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.api.client.PaymentClient
import com.dna.sdk.dnapayments.models.network.payments.*
import com.dna.sdk.dnapayments.utils.CustomKoinComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.inject

internal class PaymentsRepository : Repository, CustomKoinComponent() {

    private val paymentsClient: PaymentClient by inject()
    private val terminalId = DnaSdkCredentials.terminalId

    companion object {
        private var INSTANCE: PaymentsRepository? = null
        fun getInstance(): PaymentsRepository {
            if (INSTANCE == null) {
                INSTANCE = PaymentsRepository()
            }
            return INSTANCE!!
        }
    }

    suspend fun enrollmentPayment(
        token: String, paymentData: PaymentData
    ): ApiResponse<EnrollmentResponse> {
        return withContext(Dispatchers.IO) {
            paymentData.terminalId = terminalId

            paymentsClient.enrollmentPayment(
                String.format("Bearer %s", token),
                paymentData
            )
        }
    }

    suspend fun getTransactionById(
        token: String, id: String
    ): ApiResponse<TransactionDetailsResponse> {
        return withContext(Dispatchers.IO) {
            paymentsClient.getTransactionById(
                String.format("Bearer %s", token), id
            )
        }
    }

    suspend fun chargePayment(
        token: String, data: PaymentOperation
    ): ApiResponse<TransactionOperationResponse> {
        return withContext(Dispatchers.IO) {
            paymentsClient.chargePayment(
                String.format("Bearer %s", token), data
            )
        }
    }

    suspend fun cancelPayment(
        token: String, data: PaymentOperation
    ): ApiResponse<TransactionOperationResponse> {
        return withContext(Dispatchers.IO) {
            paymentsClient.cancelPayment(
                String.format("Bearer %s", token), data
            )
        }
    }

    suspend fun refundPayment(
        token: String, data: PaymentOperation
    ): ApiResponse<TransactionOperationResponse> {
        return withContext(Dispatchers.IO) {
            paymentsClient.refundPayment(
                String.format("Bearer %s", token), data
            )
        }
    }
}