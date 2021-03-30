package com.dna.sdk.dnapayments.models.network.payments

import androidx.annotation.Keep

@Keep
class Card(
    val accountNumber: String?,
    val expirationMonth: String?,
    val expirationYear: String?,
    val cardType: String?,
    val csc: String?
) {
    @Keep
    data class Builder(
        var accountNumber: String? = null,
        var expirationMonth: String? = null,
        var expirationYear: String? = null,
        var cardType: String? = null,
        var csc: String? = null
    ) {

        fun setAccountNumber(accountNumber: String) = apply { this.accountNumber = accountNumber }
        fun setExpirationMonth(expirationMonth: String) =
            apply { this.expirationMonth = expirationMonth }

        fun setExpirationYear(expirationYear: String) =
            apply { this.expirationYear = expirationYear }

        fun setCardType(cardType: String) = apply { this.cardType = cardType }
        fun setCsc(csc: String) = apply { this.csc = csc }

        fun build() = Card(
            accountNumber,
            expirationMonth,
            expirationYear,
            cardType,
            csc
        )
    }
}
