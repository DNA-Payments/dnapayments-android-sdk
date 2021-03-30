package com.dna.sdk.dnapayments.api.client

import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.api.service.PaymentService
import com.dna.sdk.dnapayments.models.network.payments.*
import com.dna.sdk.dnapayments.utils.Logger

internal class PaymentClient(private val service: PaymentService) {

    suspend fun enrollmentPayment(
        token: String,
        paymentData: PaymentData
    ): ApiResponse<EnrollmentResponse> {
        Logger.logEnrollment(token, paymentData)
        val response = ApiResponse.create(service.enrollmentPayment(token, paymentData))
        Logger.logResponse(response)
        return response
    }

    suspend fun getTransactionById(
        token: String,
        id: String
    ): ApiResponse<TransactionDetailsResponse> {
        Logger.logDetails(token, id)
        val response = ApiResponse.create(service.getTransactionById(token, id))
        Logger.logResponse(response)
        return response
    }

    suspend fun chargePayment(
        token: String,
        data: PaymentOperation
    ): ApiResponse<TransactionOperationResponse> =
        ApiResponse.create(service.chargePayment())

    suspend fun cancelPayment(
        token: String,
        data: PaymentOperation
    ): ApiResponse<TransactionOperationResponse> =
        ApiResponse.create(service.cancelPayment())

    suspend fun refundPayment(
        token: String,
        data: PaymentOperation
    ): ApiResponse<TransactionOperationResponse> =
        ApiResponse.create(service.refundPayment())

}