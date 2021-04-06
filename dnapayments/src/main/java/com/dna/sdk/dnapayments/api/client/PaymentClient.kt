package com.dna.sdk.dnapayments.api.client

import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.api.service.PaymentService
import com.dna.sdk.dnapayments.models.network.payments.*
import com.dna.sdk.dnapayments.utils.Logger
import com.dna.sdk.dnapayments.utils.Validator

internal class PaymentClient(private val service: PaymentService) {

    suspend fun enrollmentPayment(
        token: String,
        paymentData: PaymentData
    ): ApiResponse<EnrollmentResponse> {

        Logger.logEnrollment(token, paymentData)

        if (!Validator.isEnrollmentValidated(token, paymentData)) {
            return ApiResponse.getFieldErrorResponse()
        }

        val response = ApiResponse.create(service.enrollmentPayment(token, paymentData))

        Logger.logResponse(response)

        if (response is ApiResponse.Failure.Error) {
            when (response.error.code) {
                4132 -> {
                    response.error.message = "Your card has not been authorised, please retry."
                }
                4001 -> {
                    response.error.message =
                        "There has been a technical error and your card has not been authorised."
                }
                else -> {
                    response.error.message =
                        "Your card has not been authorised, please check the details and retry or contact your bank."
                }
            }
        }

        return response
    }

    suspend fun getTransactionById(
        token: String,
        id: String
    ): ApiResponse<TransactionDetailsResponse> {
        Logger.logDetails(token, id)

        if (!Validator.isDetailsValidated(token, id)) {
            return ApiResponse.getFieldErrorResponse()
        }

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