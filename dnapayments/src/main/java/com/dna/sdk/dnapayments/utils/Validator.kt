package com.dna.sdk.dnapayments.utils

import com.dna.sdk.dnapayments.models.network.payments.PaymentData
import java.math.BigDecimal

object Validator {

    fun isAuthFieldsValidated(
        amount: BigDecimal,
        currency: String,
        invoiceId: String,
        terminal: String
    ): Boolean {
        return !(!isValidAmount(amount) || currency.isBlank() || invoiceId.isBlank() || terminal.isBlank())
    }

    private fun isValidAmount(amount: BigDecimal?): Boolean {
        val regex = """^\d+(?:\.\d{1,2})?$""".toRegex()

        if (amount == null || amount.toDouble() <= 0) {
            return false
        } else if (!regex.matches(amount.toString())) {
            return false
        }
        return true
    }

    fun isEnrollmentValidated(token: String, paymentData: PaymentData): Boolean {
        if (paymentData.transactionType == "RECURRING" && paymentData.periodicType.isNullOrBlank()) {
            return false
        }

        return !(paymentData.invoiceId.isNullOrBlank() ||
                paymentData.terminalId.isNullOrBlank() ||
                !isValidAmount(paymentData.amount) ||
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