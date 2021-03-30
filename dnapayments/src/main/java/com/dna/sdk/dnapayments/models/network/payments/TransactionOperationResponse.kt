package com.dna.sdk.dnapayments.models.network.payments

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class TransactionOperationResponse(
	@SerializedName("accountId") val accountId: String?,
	@SerializedName("amount") val amount: Double?,
	@SerializedName("currency") val currency: String?,
	@SerializedName("errorCode") val errorCode: Long?,
	@SerializedName("id") val id: String?,
	@SerializedName("invoiceId") val invoiceId: String?,
	@SerializedName("message") val message: String?,
	@SerializedName("payoutAmount") val payoutAmount: Double?,
	@SerializedName("success") val success: Boolean?,
	@SerializedName("transactionState") val transactionState: String?
)
