package com.dna.sdk.dnapayments.models.network.payments

import androidx.annotation.Keep

@Keep
class PaymentOperation(
    val id: String?,
    val amount: Double?
) {
    @Keep
    data class Builder(
        var id: String? = null,
        var amount: Double? = null
    ) {

        fun setTxnId(id: String) = apply { this.id = id }
        fun setAmount(amount: Double) = apply { this.amount = amount }
        fun build() = PaymentOperation(
            id,
            amount
        )
    }
}
