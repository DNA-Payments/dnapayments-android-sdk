package com.dna.sdk.dnapayments.api.service

import com.dna.sdk.dnapayments.models.network.AuthToken
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface AuthService {
    @FormUrlEncoded
    @POST("/oauth2/token")
    suspend fun getUserToken(
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("invoiceId") invoiceId: String,
        @Field("amount") amount: Double,
        @Field("currency") currency: String,
        @Field("terminal") terminal: String,
        @Field("paymentFormURL") paymentFormURL: String,
        @Field("source") source: String
    ): Response<AuthToken>

    @FormUrlEncoded
    @POST("/oauth2/token")
    suspend fun getOperationsToken(
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("source") source: String
    ): Response<AuthToken>
}