package com.dna.sdk.dnapayments.models.network.payments

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class TransactionDetailsResponse(
	@SerializedName("id") val id: String?,
	@SerializedName("currency") val currency: String?,
	@SerializedName("invoiceId") val invoiceId: String?,
	@SerializedName("accountId") val accountId: String?,
	@SerializedName("rrn") val rrn: String?,
	@SerializedName("authDateTimeUTC") val authDateTimeUTC: String?,
	@SerializedName("responseCode") val responseCode: String?,
	@SerializedName("authCode") val authCode: String?,
	@SerializedName("avsResult") val avsResult: String?,
	@SerializedName("cscResult") val cscResult: String?,
	@SerializedName("payerAuthenticationResult") val payerAuthenticationResult: String?,
	@SerializedName("cardExpiryDate") val cardExpiryDate: String?,
	@SerializedName("cardPanStarred") val cardPanStarred: String?,
	@SerializedName("cardTokenId") val cardTokenId: String?,
	@SerializedName("cardSchemeName") val cardSchemeName: String?,
	@SerializedName("cardIssuingCountry") val cardIssuingCountry: String?,
	@SerializedName("transactionState") val transactionState: String?,
	@SerializedName("transactionType") val transactionType: String?,
	@SerializedName("paymentMethod") val paymentMethod: String?,
	@SerializedName("payoutAmount") val payoutAmount: Double?,
	@SerializedName("amount") val amount: Double?
)
