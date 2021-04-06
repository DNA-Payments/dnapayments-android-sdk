package com.dna.sdk.dnapayments.models.network

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorApiResponse(
    @SerializedName("errorCode") val code: Int?,
    @SerializedName("code") val authCode: Int?,
    @SerializedName("message") val message: String
)
