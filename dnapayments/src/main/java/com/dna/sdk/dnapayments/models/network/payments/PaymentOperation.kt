package com.dna.sdk.dnapayments.models.network.payments

import androidx.annotation.Keep
import java.math.BigDecimal

@Keep
class PaymentOperation(
    val id: String?,
    val amount: BigDecimal?
) {
    @Keep
    data class Builder(
        var id: String? = null,
        var amount: BigDecimal? = null
    ) {

        fun setTxnId(id: String) = apply { this.id = id }
        fun setAmount(amount: BigDecimal) = apply { this.amount = amount }
        fun build() = PaymentOperation(
            id,
            amount
        )
    }
}
