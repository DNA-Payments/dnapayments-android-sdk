package com.dna.sdk.dnapayments.models.network.payments

import androidx.annotation.Keep

@Keep
class PaymentData(
    val invoiceId: String?,
    val description: String?,
    val accountId: String?,
    val email: String?,
    val phone: String?,
    var terminalId: String?,
    val amount: Double?,
    val ipAddress: String?,
    val currency: String?,
    val transactionType: String?,
    val recurringTransactionType: String?,
    val periodicType: String?,
    val postLink: String?,
    val failurePostLink: String?,
    val card: Card?,
    val billingAddress: BillingAddress?
) {
    @Keep
    data class Builder(
        var invoiceId: String? = null,
        var description: String? = null,
        var accountId: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var terminalId: String? = null,
        var amount: Double? = null,
        var ipAddress: String? = null,
        var currency: String? = null,
        var transactionType: String? = null,
        var recurringTransactionType: String? = null,
        var periodicType: String? = null,
        var postLink: String? = null,
        var failurePostLink: String? = null,
        var card: Card? = null,
        var billingAddress: BillingAddress? = null
    ) {

        fun setInvoiceId(invoiceId: String) = apply { this.invoiceId = invoiceId }
        fun setDescription(description: String) = apply { this.description = description }
        fun setAccountId(accountId: String) = apply { this.accountId = accountId }
        fun setEmail(email: String) = apply { this.email = email }
        fun setPhone(phone: String) = apply { this.phone = phone }
        fun setTerminalId(terminal: String) = apply { this.terminalId = terminal }
        fun setAmount(amount: Double) = apply { this.amount = amount }
        fun setIpAddress(ipAddress: String) = apply { this.ipAddress = ipAddress }
        fun setCurrency(currency: String) = apply { this.currency = currency }
        fun setTransactionType(transactionType: String) =
            apply { this.transactionType = transactionType }

        fun setRecurringTransactionType(recurringTransactionType: String) =
            apply { this.recurringTransactionType = recurringTransactionType }

        fun setPeriodicType(periodicType: String) = apply { this.periodicType = periodicType }
        fun setPostLink(postLink: String) = apply { this.postLink = postLink}
        fun setFailurePostLink(failurePostLink: String) = apply { this.failurePostLink = failurePostLink }
        fun setCard(card: Card) = apply { this.card = card }
        fun setBillingAddress(billingAddress: BillingAddress) = apply { this.billingAddress = billingAddress }
        fun build() = PaymentData(
            invoiceId,
            description,
            accountId,
            email,
            phone,
            terminalId,
            amount,
            ipAddress,
            currency,
            transactionType,
            recurringTransactionType,
            periodicType,
            postLink,
            failurePostLink,
            card,
            billingAddress
        )
    }
}
