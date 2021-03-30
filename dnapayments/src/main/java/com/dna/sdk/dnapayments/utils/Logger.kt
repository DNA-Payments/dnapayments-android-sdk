package com.dna.sdk.dnapayments.utils

import android.util.Log
import com.dna.sdk.dnapayments.DnaSdkCredentials
import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.models.network.payments.PaymentData
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

object Logger {
    private val isLogging = DnaSdkCredentials.isLogging
    private const val TAG = "DnaPayments"

    fun logEnrollment(token: String, paymentData: PaymentData) {
        if (isLogging) {
            try {
                val jsonTree: JsonElement = Gson().toJsonTree(paymentData, PaymentData::class.java)
                val responseJson: JsonObject = jsonTree.asJsonObject

                val nested = responseJson.get("card").asJsonObject
                nested.addProperty("accountNumber", "***")
                nested.addProperty("csc", "***")
                d(content = "token: $token , data: $responseJson", subTag = "Request")
            } catch (ex: Exception) {
                d(content = "token: $token , data: ${ex.message}", subTag = "Request Log Error")
            }
        }
    }

    fun logDetails(token: String, id: String) {
        if (isLogging) {
            d(
                content = "token: $token , txnId: $id",
                subTag = "Request"
            )
        }
    }

    fun logAuthUserToken(
        grantType: String,
        scope: String,
        clientId: String,
        clientSecret: String,
        invoiceId: String,
        amount: Double,
        currency: String,
        terminal: String,
        paymentFormURL: String,
        source: String
    ) {
        if (isLogging) {
            d(
                content = "grantType: $grantType , scope: $scope, clientId: $clientId, clientSecret: $clientSecret, invoiceId: $invoiceId, amount: $amount, currency: $currency, terminal: $terminal, paymentFormURL: $paymentFormURL, source: $source",
                subTag = "Request"
            )
        }
    }

    fun logAuthOperations(
        grantType: String,
        scope: String,
        clientId: String,
        clientSecret: String,
        source: String
    ) {
        if (isLogging) {
            d(
                content = "grantType: $grantType , scope: $scope, clientId: $clientId, clientSecret: $clientSecret, source: $source",
                subTag = "Request"
            )
        }
    }

    fun logResponse(response: ApiResponse<Any>) {
        if (isLogging) {
            try {
                val json = Gson().toJson(response)
                d(content = json, subTag = "Response")
            } catch (ex: Exception) {
                d(content = "data: ${ex.message}", subTag = "Response Log Error")
            }
        }
    }

    private fun d(content: String?, subTag: String = "") {
        if (isLogging) {
            Log.d(TAG + "_" + subTag, content)
        }
    }

    fun v(string: String?) {
        if (isLogging) {
            Log.v(TAG, string)
        }
    }

    fun i(string: String?) {
        if (isLogging) {
            Log.i(TAG, string)
        }
    }

    fun e(string: String?) {
        if (isLogging) {
            Log.e(TAG, string)
        }
    }

    fun w(string: String?) {
        if (isLogging) {
            Log.w(TAG, string)
        }
    }
}