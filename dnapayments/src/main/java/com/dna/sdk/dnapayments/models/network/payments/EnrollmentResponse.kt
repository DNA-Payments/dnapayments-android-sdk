package com.dna.sdk.dnapayments.models.network.payments

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Keep
@Parcelize
data class EnrollmentResponse(
    val amount: Double?,
    val authDateTimeUTC: String?,
    val cardExpiryDate: String?,
    val cardPanStarred: String?,
    val cardSchemeName: String?,
    @SerializedName("cardTokenId")
    val cardTokenID: String?,
    val currency: String?,
    val errorCode: Long?,
    val id: String?,
    @SerializedName("invoiceId")
    val invoiceID: String?,
    val message: String?,
    val rrn: String?,
    val success: Boolean?,
    val threeD: ThreeD?
) : Parcelable

@Keep
@Parcelize
data class ThreeD(
    val acsUrl: String?,
    val cardholderAuthenticationStatus: String?,
    val md: String?,
    val pareq: String?,
    val termUrl: String?,
    val version: String?
) : Parcelable