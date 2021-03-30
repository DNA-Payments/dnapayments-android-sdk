package com.dna.sdk.dnapayments.models.network

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PaymentResult(
    val id: String? = null,
    var result: Result? = null
) : Parcelable

@Keep
@Parcelize
data class Result(
    val errorCode: Long? = null,
    val errorMessage: String? = null,
    val reference: String? = null,
    val result: String? = null
) : Parcelable