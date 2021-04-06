package com.dna.sdk.dnapayments.utils

import com.dna.sdk.dnapayments.models.network.payments.PaymentData

object Validator {

    fun isAuthFieldsValidated(
        amount: Double,
        currency: String,
        invoiceId: String,
        terminal: String
    ): Boolean {
        return !(amount <= 0 || currency.isBlank() || invoiceId.isBlank() || terminal.isBlank())
    }

    fun isEnrollmentValidated(token: String, paymentData: PaymentData): Boolean {
        if (paymentData.transactionType == "RECURRING" && paymentData.periodicType.isNullOrBlank()) {
            return false
        }

        return !(paymentData.invoiceId.isNullOrBlank() ||
                paymentData.terminalId.isNullOrBlank() ||
                paymentData.amount == null ||
                paymentData.currency.isNullOrBlank() ||
                paymentData.ipAddress.isNullOrBlank() ||
                paymentData.email.isNullOrBlank() ||
                paymentData.card == null ||
                paymentData.billingAddress == null ||
                paymentData.card.accountNumber.isNullOrBlank() ||
                paymentData.card.expirationMonth.isNullOrBlank() ||
                paymentData.card.expirationYear.isNullOrBlank() ||
                paymentData.card.csc.isNullOrBlank() ||
                paymentData.billingAddress.country.isNullOrBlank() ||
                paymentData.billingAddress.city.isNullOrBlank() ||
                paymentData.billingAddress.streetAddress1.isNullOrBlank() ||
                paymentData.billingAddress.firstName.isNullOrBlank() ||
                paymentData.billingAddress.lastName.isNullOrBlank() ||
                paymentData.billingAddress.postalCode.isNullOrBlank() ||
                token.isBlank())
    }

    fun isDetailsValidated(token: String, id: String): Boolean {
        return !(id.isBlank() ||
                token.isBlank())
    }

}