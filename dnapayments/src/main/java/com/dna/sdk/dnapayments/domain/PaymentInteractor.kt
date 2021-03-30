package com.dna.sdk.dnapayments.domain

import androidx.annotation.Keep
import com.dna.sdk.dnapayments.models.network.payments.PaymentData
import com.dna.sdk.dnapayments.models.network.payments.PaymentOperation
import com.dna.sdk.dnapayments.repository.PaymentsRepository

@Keep class PaymentInteractor {
    init {
    }

    @Keep
    companion object {
        private var INSTANCE: PaymentInteractor? = null
        fun getInstance(): PaymentInteractor {
            if (INSTANCE == null) {
                INSTANCE = PaymentInteractor()
            }
            return INSTANCE!!
        }
    }

    suspend fun enrollmentPayment(
        token: String, paymentData: PaymentData
    ) = PaymentsRepository.getInstance().enrollmentPayment(token, paymentData)

    suspend fun getTransactionById(
        token: String, id: String
    ) = PaymentsRepository.getInstance().getTransactionById(token, id)

    private suspend fun chargePayment(
        token: String, data: PaymentOperation
    ) = PaymentsRepository.getInstance().chargePayment(token, data)

    private suspend fun cancelPayment(
        token: String, data: PaymentOperation
    ) = PaymentsRepository.getInstance().cancelPayment(token, data)

    private suspend fun refundPayment(
        token: String, data: PaymentOperation
    ) = PaymentsRepository.getInstance().refundPayment(token, data)
}




