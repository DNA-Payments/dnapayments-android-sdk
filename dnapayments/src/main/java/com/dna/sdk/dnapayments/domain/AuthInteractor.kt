package com.dna.sdk.dnapayments.domain

import androidx.annotation.Keep
import com.dna.sdk.dnapayments.repository.AuthRepository
import com.dna.sdk.dnapayments.utils.DnaConstants

@Keep class AuthInteractor {
    init {
    }

    @Keep
    companion object {
        private var INSTANCE: AuthInteractor? = null
        fun getInstance(): AuthInteractor {
            if (INSTANCE == null) {
                INSTANCE = AuthInteractor()
            }
            return INSTANCE!!
        }
    }

    suspend fun getUserToken(
        invoiceId: String,
        amount: Double,
        currency: String,
        paymentFormUrl: String
    ) = AuthRepository.getInstance()
        .getUserToken(
            DnaConstants.grantType,
            DnaConstants.userTokenScope,
            invoiceId,
            amount,
            currency,
            paymentFormUrl
        )

    suspend fun getOperationsToken() = AuthRepository.getInstance()
        .getOperationsToken(DnaConstants.grantType, DnaConstants.operationsScope)
}




