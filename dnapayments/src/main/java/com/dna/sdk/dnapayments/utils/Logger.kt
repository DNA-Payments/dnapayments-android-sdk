package com.dna.sdk.dnapayments.utils

import android.util.Log
import com.dna.sdk.dnapayments.DnaSdkCredentials
import com.dna.sdk.dnapayments.api.ApiResponse
import com.dna.sdk.dnapayments.models.network.PaymentResult
import com.dna.sdk.dnapayments.models.network.payments.PaymentData
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.math.BigDecimal

object Logger {
    private val isLogging = DnaSdkCredentials.isLogging
    private const val TAG = "DnaPayments"

    fun logEnrollment(token: String, paymentData: PaymentData) {
        if (isLogging) {
            try {
                val jsonTree: JsonElement = Gson().toJsonTree(paymentData, PaymentData::class.java)
                val responseJson: JsonObject = jsonTree.asJsonObject

                var cardNumber = paymentData.card?.accountNumber.orEmpty()
                var maskedCardNumber = "-****-"
                if(cardNumber.length >= 16) {
                    maskedCardNumber = cardNumber.substring(0,6) + maskedCardNumber
                    maskedCardNumber += cardNumber.substring(cardNumber.length - 4)
                }

                val nested = responseJson.get("card").asJsonObject
                nested.addProperty("accountNumber", maskedCardNumber)
                nested.addProperty("csc", "***")
                e(content = "token: $token , data: $responseJson", subTag = "Request")
            } catch (ex: Exception) {
                e(content = "token: $token , data: ${ex.message}", subTag = "Request Log Error")
            }
        }
    }

    fun logDetails(token: String, id: String) {
        if (isLogging) {
            e(
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
        amount: BigDecimal,
        currency: String,
        terminal: String,
        paymentFormURL: String,
        source: String
    ) {
        if (isLogging) {
            e(
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
            e(
                content = "grantType: $grantType , scope: $scope, clientId: $clientId, clientSecret: $clientSecret, source: $source",
                subTag = "Request"
            )
        }
    }

    fun logResponse(response: ApiResponse<Any>) {
        if (isLogging) {
            try {
                val json = Gson().toJson(response)
                e(content = json, subTag = "Response")
            } catch (ex: Exception) {
                e(content = "data: ${ex.message}", subTag = "Response Log Error")
            }
        }
    }

    fun logPaymentResult(result: PaymentResult) {
        if (isLogging) {
            try {
                val json = Gson().toJson(result)
                e(content = json, subTag = "Response")
            } catch (ex: Exception) {
                e(content = "data: ${ex.message}", subTag = "Response Log Error")
            }
        }
    }

    private fun e(content: String?, subTag: String = "") {
        if (isLogging) {
            Log.e(TAG + "_" + subTag, content)
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