package com.dna.sdk.dnapayments.api.service

import com.dna.sdk.dnapayments.models.network.payments.*
import retrofit2.Response
import retrofit2.http.*

internal interface PaymentService {
    @POST("payments/card/operation/enrollment")
    suspend fun enrollmentPayment(
        @Header("authorization") authorization: String,
        @Body body: PaymentData
    ): Response<EnrollmentResponse>

    @GET("/transaction/{id}/search")
    suspend fun getTransactionById(
        @Header("authorization") authorization: String,
        @Path("id") id: String
    ): Response<TransactionDetailsResponse>

    suspend fun chargePayment(): Response<TransactionOperationResponse>

    suspend fun cancelPayment(): Response<TransactionOperationResponse>

    suspend fun refundPayment(): Response<TransactionOperationResponse>
}